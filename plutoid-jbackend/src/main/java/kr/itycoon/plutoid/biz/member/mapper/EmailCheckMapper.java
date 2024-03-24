package kr.itycoon.plutoid.biz.member.mapper;

import kr.itycoon.plutoid.biz.common.model.IssueForEnum;
import org.apache.ibatis.annotations.Mapper;

import kr.itycoon.plutoid.biz.member.domain.EmailCheck;

@Mapper
public interface EmailCheckMapper {

    /**
     * 이메일 확인 생성
     */
    int insert(EmailCheck emailCheck);

    /**
     * 이메일 확인 조회
     */
    EmailCheck findEmailCheck(String email, String checkCode, IssueForEnum issueFor);

}
