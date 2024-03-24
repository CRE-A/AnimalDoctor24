package kr.itycoon.plutoid.biz.member.service;

import kr.itycoon.plutoid.biz.common.model.IssueForEnum;
import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import kr.itycoon.plutoid.biz.llm.domain.LLMModel;
import kr.itycoon.plutoid.biz.llm.service.LLMService;
import kr.itycoon.plutoid.biz.member.domain.*;
import kr.itycoon.plutoid.biz.member.dto.*;
import kr.itycoon.plutoid.biz.member.mapper.EmailCheckMapper;
import kr.itycoon.plutoid.biz.member.mapper.MemberMapper;
import kr.itycoon.plutoid.biz.member.mapper.SettingMapper;
import kr.itycoon.plutoid.global.common.CommonData;
import kr.itycoon.plutoid.global.common.CommonDataHolder;
import kr.itycoon.plutoid.global.error.exception.BizException;
import kr.itycoon.plutoid.global.error.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MemberService.class);
    private final MemberMapper memberMapper;
    private final EmailCheckMapper emailCheckMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailCheckService emailCheckService;
    private final LLMService llmService;
    private final SettingService settingService;
    private final SettingMapper settingMapper;

    /**
     * 이메일 확인 등록
     */
    public int createEmailCheck(String email, IssueForEnum issueFor) {

        // itycoon.kr 이메일만 허용
        if (!email.substring(email.indexOf('@'))
                  .equals("@itycoon.kr")) {
            throw new BizException("도메인이 `itycoon.kr` 이 아닙니다.");
        }

        // 이메일, 랜덤 검증코드 base64 인코딩 (checkCode값 fix : 123456)
        String checkCode = "123456";

        // 이메일 중복여부 확인
        Member memberDto = memberMapper.findMemberByEmail(email);

        if (issueFor == IssueForEnum.JOIN) {
            if (memberDto != null) {
                throw new ConflictException("중복된 이메일 입니다.");
            }
        } else
            if (issueFor == IssueForEnum.FORGOT) {
                if (memberDto == null) {
                    throw new BizException("가입된 이메일이 아닙니다.");
                }
                if (memberDto.getActiveYn() == "N") {
                    throw new BizException("활성화된 회원이 아닙니다.");
                }
            }

        // 이메일 전송 (생략)
        // 이메일 & 검증코드 저장
        final Date now = new Date();
        EmailCheck emailCheck = new EmailCheck(email, checkCode, issueFor, now, now);
        return emailCheckService.createEmailCheck(emailCheck);
    } //

    /**
     * 이메일 확인 검증
     */
    public EmailCheck verifyEmailCheck(String email, String checkCode, IssueForEnum issueFor) {

        // 이메일 중복여부 확인 (회원가입 시에만)
        Member member = memberMapper.findMemberByEmail(email);
        log.info("이메일 중복여부 확인 member : " + member);
        if (issueFor == IssueForEnum.JOIN) {
            if (member != null) {
                throw new ConflictException("중복된 이메일입니다.");
            }
        }

        // 이메일 검증 코드 확인
        EmailCheck emailCheckDto = emailCheckMapper.findEmailCheck(email, checkCode, issueFor);
        log.debug("이메일 검증 코드 확인 : " + emailCheckDto);
        if (emailCheckDto == null) throw new BizException("확인코드가 틀렸습니다.");

        return emailCheckDto;
    }

    /**
     * 회원 정보 저장 (회원가입)
     */
    public Member saveMember(MemberRequestDto memberRequestDto) throws Exception {

        // 이메일 확인 검증
        EmailCheck emailCheck = this.verifyEmailCheck(memberRequestDto.getEmail(), memberRequestDto.getCheckCode(), IssueForEnum.JOIN);

        // 입력 비밀번호, 비밀번호(확인) 일치여부 검증
        if (!memberRequestDto.getPassword()
                             .equals(memberRequestDto.getPasswordConfirm())) {
            throw new BizException("비밀번호가 일치하지 않습니다.");
        }
        // 비밀번호 암호화 처리
        memberRequestDto.encodingPassword(passwordEncoder);

        // 회원 저장
        UUID uuid = UUID.randomUUID();
        Date now = new Date();
        Member member = Member.builder()
                              .memberId(uuid.toString())
                              .memberName(memberRequestDto.getMemberName())
                              .email(memberRequestDto.getEmail())
                              .password(memberRequestDto.getPassword())
                              .memberRole(MemberRoleEnum.MEMBER)
                              .photoUrl("")
                              .activeYn("Y")
                              .deleteYn("N")
                              .joinDate(now)
                              .deleteDate(null)
                              .build();

        // 회원 LLM 모델 기본 설정값 저장
        this.createDefaultLlmSetting(member.getMemberId());

        memberMapper.mergeMember(member);


        return member;
    }

    /**
     * 로그인
     */
    public Member findMemberByEmail(String email) {
        return memberMapper.findMemberByEmail(email);
    }

    public Member findMemberByKey(String memberId) {
        return memberMapper.findMemberByKey(memberId);
    }


    /**
     * 비로그인 사용자 비밀번호 변경
     */
    public int changePwdForMemberWithoutLogin(NonLoginMember nonLoginMember) {
        // 입력 비밀번호, 비밀번호(확인) 일치 여부 검증
        if (!nonLoginMember.getPassword()
                           .equals(nonLoginMember.getPasswordConfirm())) {
            throw new BizException("비밀번호가 일치하지 않습니다.");
        }

        // 회원 조회
        Member member = memberMapper.findMemberByEmail(nonLoginMember.getEmail());
        log.debug("member : {} ", member);

        if (member == null) {
            throw new BizException("회원이 아닙니다.");
        }
        if (!"Y".equals(member.getActiveYn())) {
            throw new BizException("활성화된 회원이 아닙니다.");
        }

        // 검증코드 확인
        EmailCheck emailCheck = this.verifyEmailCheck(nonLoginMember.getEmail(), nonLoginMember.getCheckCode(), IssueForEnum.FORGOT);

        // 비밀번호 암호화 처리
        nonLoginMember.encodingPassword(passwordEncoder);

        // 비밀번호 변경
        member.setPassword(nonLoginMember.getPassword());

        return memberMapper.updatePwdByEmail(member);
    }

    /**
     * 회원조회
     */
    public MemberSettingResponse findMemberWithSetting(String memberId) {

        // 로그인 사용자 본인여부 확인

        // 회원조회
        Member member = memberMapper.findMemberByKey(memberId);
        log.debug("회원조회 member : {} ", member);
        if (member.equals(null) || member == null) {
            throw new BizException("존재하지 않는 회원입니다.");
        }
        if (member.getActiveYn()
                  .equals('N') || member.getActiveYn() == "N") {
            throw new BizException("사용 정지된 사용자입니다.");
        }

        // 설정조회
        Setting setting = settingMapper.findSettingByKey(memberId);
        log.debug("설정조회 setting : {} ", setting);
        if (setting.equals(null) || setting == null) {
            throw new BizException("처리중 오류가 발생하였습니다.");
        }

        List<SettingDetail> settingDetailsList = settingMapper.selectSettingDetails(memberId);
        SettingDetails settingDetails = SettingDetails.builder()
                                                      .build();
        for (SettingDetail settingDetail : settingDetailsList) {
            switch (settingDetail.getSettingKey()) {
                case "temperature":
                    settingDetails.setTemperature(settingDetail.getSettingValue());
                    break;
                case "max_tokens":
                    settingDetails.setMax_tokens(settingDetail.getSettingValue());
                    break;
                case "top_p":
                    settingDetails.setTop_p(settingDetail.getSettingValue());
                    break;
                case "frequency_penalty":
                    settingDetails.setFrequency_penalty(settingDetail.getSettingValue());
                    break;
                case "presence_penalty":
                    settingDetails.setPresence_penalty(settingDetail.getSettingValue());
                    break;
                default:
                    throw new BizException("유효하지 않은 값입니다.");
            }
        }
        setting.setSettingDetails(settingDetails);
        MemberSettingResponse memberSettingResponse = MemberSettingResponse.builder()
                                                                           .member(member)
                                                                           .setting(setting)
                                                                           .build();
        return memberSettingResponse;


    }

    /**
     * 회원 정보 수정
     */
    public MemberUpdateResponse updateMember(String memberId, String memberName, String photoUrl) {

        // 로그인 사용자 본인 여부 확인
        CommonData commonData = CommonDataHolder.getCommonData();
        if (!commonData.getMemberId()
                       .equals(memberId)) {
            throw new BizException("본인 정보만 변경 가능합니다.");
        }

        // 회원 조회
        Member member = memberMapper.findMemberByKey(memberId);
        if (member == null) {
            throw new BizException("존재하지 않는 회원입니다.");
        }
        if (!"Y".equals(member.getActiveYn())) {
            throw new BizException("사용이 정지된 회원입니다.");
        }

        // 회원 저장 및 리턴
        if (!memberName.equals(null)) {
            member.setMemberName(memberName);
        }
        member.setPhotoUrl(photoUrl);
        int cnt = memberMapper.updateMember(member);

        member = memberMapper.findMemberByKey(memberId);
        MemberUpdateResponse memberUpdateResponse = MemberUpdateResponse.builder()
                                                                        .joinDate(member.getJoinDate())
                                                                        .email(member.getEmail())
                                                                        .memberRole(member.getMemberRole())
                                                                        .memberId(memberId)
                                                                        .memberName(member.getMemberName())
                                                                        .photoUrl(member.getPhotoUrl())
                                                                        .build();
        return memberUpdateResponse;
    }

    /**
     * 비밀번호 변경을 위한 이메일확인 등록
     */
    public Integer emailCheckForMember(String memberId) {

        // 본인만 비밀번호 변경을 요청할 수 있음
        CommonData commonData = CommonDataHolder.getCommonData();
        if (!commonData.getMemberId()
                       .equals(memberId)) {
            throw new BizException("본인 정보만 변경 가능합니다.");
        }

        // 회원 조회
        Member member = memberMapper.findMemberByKey(memberId);
        if (member == null) {
            throw new BizException("존재하지 않는 회원입니다.");
        }
        if (!"Y".equals(member.getActiveYn())) {
            throw new BizException("사용이 정지된 회원입니다.");
        }

        // 랜덤 검증코드 생성
        String checkCode = "123456";

        // 이메일 & 검증코드 저장
        final Date now = new Date();
        EmailCheck emailCheck = new EmailCheck(member.getEmail(), checkCode, IssueForEnum.CHANGE, now, now);
        return emailCheckService.createEmailCheck(emailCheck);

    }

    /**
     * 비밀번호 변경을 위한 이메일 검증
     */
    public VerifyCodeForMemberResponse emailCheckVerifyCodeForMember(String checkCode, String memberId) {
        // 본인만 비밀번호 변경을 위한 이메일 확인코드를 입력할 수 있음
        CommonData commonData = CommonDataHolder.getCommonData();
        if (!commonData.getMemberId()
                       .equals(memberId)) {
            throw new BizException("본인 정보만 변경 가능합니다.");
        }

        // 회원 조회
        Member member = memberMapper.findMemberByKey(memberId);
        if (member == null) {
            throw new BizException("존재하지 않는 회원입니다.");
        }
        if (!"Y".equals(member.getActiveYn())) {
            throw new BizException("사용이 정지된 회원입니다.");
        }

        // 이메일 검증코드 확인
        EmailCheck emailCheck = this.verifyEmailCheck(member.getEmail(), checkCode, IssueForEnum.CHANGE);
        return VerifyCodeForMemberResponse.builder()
                                          .memberId(memberId)
                                          .checkCode(checkCode)
                                          .issueFor(emailCheck.getIssueFor())
                                          .build();
    }

    /**
     * 로그인 사용자 비밀번호 변경
     */
    public Integer changePwdForMember(String memberId, String checkCode, String password, String passwordConfirm) {
        // 로그인 사용자 본인여부 확인
        CommonData commonData = CommonDataHolder.getCommonData();
        if (!commonData.getMemberId()
                       .equals(memberId)) {
            throw new BizException("본인 정보만 변경 가능합니다.");
        }

        // 입력 비밀번호, 비밀번호(확인) 일치여부 검증
        if (!password.equals(passwordConfirm)) {
            throw new BizException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 암호화 처리
        String encodedPassword = passwordEncoder.encode(password);

        // 회원 조회
        Member member = memberMapper.findMemberByKey(memberId);
        if (member == null) {
            throw new BizException("존재하지 않는 회원입니다.");
        }
        if (!"Y".equals(member.getActiveYn())) {
            throw new BizException("사용이 정지된 회원입니다.");
        }

        // 검증코드 확인
        this.verifyEmailCheck(member.getEmail(), checkCode, IssueForEnum.CHANGE);

        // 회원 저장 및 리턴
        member.setPassword(encodedPassword);
        return memberMapper.updatePwdByKey(member);
    }

    /**
     * 회원탈퇴
     */
    public Integer deleteMember(String email, String memberId) {
        // 로그인 사용자 본인 여부 확인
        CommonData commonData = CommonDataHolder.getCommonData();
        if (!commonData.getMemberId()
                       .equals(memberId)) {
            throw new BizException("본인 정보만 변경 가능합니다.");
        }

        // 회원 조회
        Member member = memberMapper.findMemberByKey(memberId);
        if (member == null) {
            throw new BizException("존재하지 않는 회원입니다.");
        }
        if (!"Y".equals(member.getActiveYn())) {
            throw new BizException("사용이 정지된 회원입니다.");
        }
        if (!commonData.getEmail()
                       .equals(email)) {
            throw new BizException("이메일이 일치하지 않습니다.");
        }


        // 회원 탈퇴
        return memberMapper.deleteMember(member);

    }


    /**
     * 회원 기본 LLM설정 저장
     */
    public Setting createDefaultLlmSetting(String memberId) throws NoSuchFieldException {
        // 현재 LLM공급자는 OPEN_AI만 가능

        // 기본 모델 조회
        LLMModel model = llmService.findDefaultLlmModel("OPEN_AI");
        log.debug("기본 모델 조회 LLMModel : ", model);

        // 설정 저장
        Setting setting = Setting.builder()
                                 .memberId(memberId)
                                 .settingNo(settingService.nextSettingNo(memberId))
                                 .llmProvider("OPEN_AI")
                                 .llmModel(model.getLlmModel())
                                 .settingDate(new Date())
                                 .defaultValueYn("Y")
                                 .build();
        int cnt = settingService.createSetting(setting);
        log.debug("설정저장 setting : ", setting);


        // 기본 파라미터 값
        Map<String, String> defaults = new HashMap<>();
        defaults.put("temperature", "1");
        defaults.put("max_tokens", "256");
        defaults.put("top_p", "1");
        defaults.put("frequency_penalty", "0");
        defaults.put("presence_penalty", "0");

        // 설정 상세 저장
        SettingDetails settingDetails = SettingDetails.builder()
                                                      .build();
        log.debug("settingDetials 생성 : {}", settingDetails);
        for (Map.Entry<String, String> entry : defaults.entrySet()) {

            SettingDetail settingDetail = SettingDetail.builder()
                                                       .memberId(memberId)
                                                       .settingNo(setting.getSettingNo())
                                                       .settingKey(entry.getKey())
                                                       .settingValue(entry.getValue())
                                                       .build();
            int sdCnt = settingMapper.insertSettingDetails(settingDetail);
        }

        log.debug("setting settingDetails가 들어왔는지 확인 : {}", setting);
        return setting;
    }

    /**
     * 회원 LLM설정 저장
     */

    public LLMSettingResponse saveLLMForMember(LLMSettingRequest llmSettingRequest) {
        // 현재 LLM공급자는 OPEN_AI만 가능

        // 설정가능한 LLM모델여부 확인
        LLMModel model = llmService.findLlmModel(llmSettingRequest.getLlmModel());
        if (model == null) {
            throw new BizException("입력값 오류");
        }

        // 설정 저장
        Setting setting = Setting.builder()
                                 .memberId(llmSettingRequest.getMemberId())
                                 .settingNo(settingService.nextSettingNo(llmSettingRequest.getMemberId()))
                                 .llmProvider("OPEN_AI")
                                 .llmModel(model.getLlmModel())
                                 .settingDate(new Date())
                                 .defaultValueYn("Y")
                                 .build();
        int cnt = settingService.createSetting(setting);
        log.debug("[회원 LLM설정 저장] 설정저장 setting : {} ", setting);

        // 설정 가능한 파라미터 값 정보
        Map<String, HashMap<String, Object>> parameters = new HashMap<>();
        parameters.put("temperature", createParamDetail("float", 0, 2));
        parameters.put("max_tokens", createParamDetail("int", 1, 9999999));
        parameters.put("top_p", createParamDetail("float", 0, 1));
        parameters.put("frequency_penalty", createParamDetail("float", 0, 2));
        parameters.put("presence_penalty", createParamDetail("float", 0, 2));
        log.debug("[회원 LLM설정 저장] parameters : {} ", parameters);

        // 설정 상세 저장
        SettingDetails settingDetails = llmSettingRequest.getSettingDetails();
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("temperature", settingDetails.getTemperature());
        valueMap.put("max_tokens", settingDetails.getMax_tokens());
        valueMap.put("top_p", settingDetails.getTop_p());
        valueMap.put("frequency_penalty", settingDetails.getFrequency_penalty());
        valueMap.put("presence_penalty", settingDetails.getPresence_penalty());
        log.debug("[회원 LLM설정 저장] settingDetails : {} ", settingDetails);
        // 최소 & 최대값 검증
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            String key = entry.getKey();
            HashMap<String, Object> checkField = parameters.get(key);
//            if ("float".equals(checkField.get("type"))) {
//                Float floatValue = Float.parseFloat(String.valueOf(entry.getValue())); // 회원 변경 요청값
//                if (Float.parseFloat(String.valueOf(checkField.get("min"))) > floatValue || Float.parseFloat(String.valueOf(checkField.get("max"))) < floatValue) {
//                    throw new BizException("common.input_error");
//                }
//            } else
//                if ("int".equals(checkField.get("type"))) {
//                    Integer intValue = Integer.parseInt(String.valueOf(entry.getValue()));// 회원 변경 요청값
//                    if (Integer.parseInt(String.valueOf(checkField.get("min"))) > intValue || Integer.parseInt(String.valueOf(checkField.get("max"))) < intValue) {
//                        throw new BizException("common.input_error");
//                    }
//                }

            // 최대 토큰값 검증

            if ("max_tokens".equals(key) && Integer.parseInt(model.getMaxToken()) > Float.parseFloat(String.valueOf(checkField.get("max")))) {
                throw new BizException("common.input_error");
            }

            SettingDetail settingDetail = SettingDetail.builder()
                                                       .memberId(llmSettingRequest.getMemberId())
                                                       .settingNo(setting.getSettingNo())
                                                       .settingKey(entry.getKey())
                                                       .settingValue(String.valueOf(entry.getValue()))
                                                       .build();
            int sdCnt = settingMapper.insertSettingDetails(settingDetail);


        } // for

        log.debug("[회원 LLM설정 저장] settingDetails : {} ", settingDetails);

        LLMSettingResponse llmSettingResponse = LLMSettingResponse.builder()
                                                                  .memberId(llmSettingRequest.getMemberId())
                                                                  .llmProvider(llmSettingRequest.getLlmProvider())
                                                                  .llmModel(llmSettingRequest.getLlmModel())
                                                                  .settingDetails(settingDetails)
                                                                  .build();
        log.debug("[회원 LLM설정 저장] llmSettingResponse : {} ", llmSettingResponse);
        return llmSettingResponse;

    } // saveLLMForMember()

    private HashMap<String, Object> createParamDetail(String type, float min, float max) {
        HashMap<String, Object> detail = new HashMap<>();
        detail.put("type", type);
        detail.put("min", min);
        detail.put("max", max);
        return detail;
    }


}