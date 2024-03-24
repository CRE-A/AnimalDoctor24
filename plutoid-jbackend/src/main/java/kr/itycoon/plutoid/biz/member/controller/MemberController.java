package kr.itycoon.plutoid.biz.member.controller;

import kr.itycoon.plutoid.biz.member.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import kr.itycoon.plutoid.biz.member.domain.EmailCheck;
import kr.itycoon.plutoid.biz.member.domain.Member;
import kr.itycoon.plutoid.biz.member.service.EmailCheckService;
import kr.itycoon.plutoid.biz.member.service.MemberService;
import kr.itycoon.plutoid.global.common.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final EmailCheckService emailCheckService;
    private final MemberService memberService;

    /**
     * 이메일 확인 등록 (regist-email)
     */
    @PostMapping("/api/v1/members/email")
    public ResponseEntity<CommonResult> emailCheck(final @Valid @RequestBody EmailCheckRegisterRequest emailCheckRequest) throws Exception {

        log.info("이메일 확인 등록 (regist-email) : " + emailCheckRequest);

        int result = memberService.createEmailCheck(emailCheckRequest.getEmail(), emailCheckRequest.getIssueFor());

        return new ResponseEntity<CommonResult>(new CommonResult(emailCheckRequest), HttpStatus.OK);
    }


    /**
     * 이메일 확인 검증 (verify)
     */
    @PutMapping("/api/v1/members/email")
    public ResponseEntity<CommonResult> emailCheckVerifyCode(final @Valid @RequestBody EmailCheckVerifyRequest emailCheckVerifyRequest) throws Exception {

        EmailCheck EmailCheck = memberService.verifyEmailCheck(
                emailCheckVerifyRequest.getEmail(),
                emailCheckVerifyRequest.getCheckCode(),
                emailCheckVerifyRequest.getIssueFor()
        );

        return new ResponseEntity<CommonResult>(new CommonResult(EmailCheck), HttpStatus.OK);
    }

    /**
     * 회원 생성 (register)
     */
    @PostMapping("/api/v1/members")
    public ResponseEntity<CommonResult> registerMember(final @Valid @RequestBody MemberRequestDto memberRequestDto) throws Exception {

        Member Member = memberService.saveMember(memberRequestDto);

        return new ResponseEntity<CommonResult>(new CommonResult(Member), HttpStatus.OK);
    }

    /**
     * 비로그인 사용자 비밀번호 변경
     */
    @PutMapping("/api/v1/members/password")
    public ResponseEntity<CommonResult> changePasswordForMemberWithoutLogin(@Valid @RequestBody NonLoginMember nonLoginMember) {

        log.debug("nonLoginMember : {}", nonLoginMember);

        int result = memberService.changePwdForMemberWithoutLogin(nonLoginMember);

        return new ResponseEntity<CommonResult>(new CommonResult(null), HttpStatus.OK);
    }

    /**
     * 회원 정보 조회
     */
    @GetMapping("/api/v1/members/{memberId}")
    public ResponseEntity<CommonResult> getMemberInfo(@PathVariable("memberId") final String memberId) {

        MemberSettingResponse memberSettingResponse = memberService.findMemberWithSetting(memberId);

        return new ResponseEntity<CommonResult>(new CommonResult(memberSettingResponse), HttpStatus.OK);
    }

    /**
     * 회원 정보 수정
     */
    @PutMapping("/api/v1/members/{memberId}")
    public ResponseEntity<CommonResult> updateMemberInfo(@Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {

        MemberUpdateResponse memberUpdateResponse = memberService.updateMember(memberUpdateRequest.getMemberId(), memberUpdateRequest.getMemberName(), memberUpdateRequest.getPhotoUrl());

        return new ResponseEntity<CommonResult>(new CommonResult(memberUpdateResponse), HttpStatus.OK);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/api/v1/members/{memberId}")
    public ResponseEntity<CommonResult> deleteMember(@Valid @RequestBody MemberDeleteRequest memberDeleteRequest) {

        int result = memberService.deleteMember(memberDeleteRequest.getEmail(), memberDeleteRequest.getMemberId());

        return new ResponseEntity<CommonResult>(new CommonResult(null), HttpStatus.OK);
    }

    /**
     * 비밀번호 변경을 위한 본인 확인 메일 발송
     */
    @PostMapping("/api/v1/members/{memberId}/email")
    public ResponseEntity<CommonResult> emailCheckInAuthenticated(@PathVariable("memberId") final String memberId) {

        int result = memberService.emailCheckForMember(memberId);

        return new ResponseEntity<CommonResult>(new CommonResult(null), HttpStatus.OK);
    }

    /**
     * 비밀번호 변경을 위한 이메일 확인 검증
     */
    @PutMapping("/api/v1/members/{memberId}/email")
    public ResponseEntity<CommonResult> emailCheckVerifyCodeForMember(@PathVariable("memberId") final String memberId, @RequestBody Map<String, String> param) {

        VerifyCodeForMemberResponse verifyCodeForMemberResponse = memberService.emailCheckVerifyCodeForMember(param.get("checkCode"), memberId);
        log.debug("param.get('checkCode') : {}", param.get("checkCode"));

        return new ResponseEntity<CommonResult>(new CommonResult(verifyCodeForMemberResponse), HttpStatus.OK);
    }

    /**
     * 로그인 사용자 비밀번호 변경
     */
    @PutMapping("/api/v1/members/{memberId}/password")
    public ResponseEntity<CommonResult> changePwdForMember(@PathVariable("memberId") final String memberId, @RequestBody Map<String, String> param) {
        int result = memberService.changePwdForMember(memberId, param.get("checkCode"), param.get("password"), param.get("passwordConfirm"));
        return new ResponseEntity<CommonResult>(new CommonResult(null), HttpStatus.OK);
    }

    /**
     * 회원 LLM모델 설정 등록
     */
    @PostMapping("/api/v1/members/{memberId}/llm")
    public ResponseEntity<CommonResult> saveLLMForMember(@RequestBody LLMSettingRequest llmSettingRequest) {
        log.debug("llmSettingRequest : {}", llmSettingRequest);
        LLMSettingResponse llmSettingResponse = memberService.saveLLMForMember(llmSettingRequest);
        log.debug("llmSettingResponse : {}", llmSettingResponse);
        return new ResponseEntity<CommonResult>(new CommonResult(llmSettingResponse), HttpStatus.OK);
    }
}
