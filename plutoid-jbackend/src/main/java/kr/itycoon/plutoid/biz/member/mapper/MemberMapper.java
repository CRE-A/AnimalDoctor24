package kr.itycoon.plutoid.biz.member.mapper;

import kr.itycoon.plutoid.biz.common.model.IssueForEnum;
import org.apache.ibatis.annotations.Mapper;
import kr.itycoon.plutoid.biz.member.domain.Member;

@Mapper
public interface MemberMapper {

    /**
     * 회원 생성 (회원가입)
     */
    int mergeMember(Member member);

    /**
     * 회원 조회 (memberId)
     */
    Member findMemberByKey(String memberId);

    /**
     * 회원 조회 (email)
     */
    Member findMemberByEmail(String email);

    /**
     * 비로그인 사용자 비밀번호 변경
     */
    Integer updatePwdByEmail(Member member);

    /**
     * 회원 탈퇴
     */
    Integer deleteMember(Member member);

    /**
     * 회원 정보 수정
     */
    int updateMember(Member member);

    /**
     * 로그인 사용자 비밀번호 변경
     */
    int updatePwdByKey(Member member);

}
