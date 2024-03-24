package kr.itycoon.plutoid;

import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import kr.itycoon.plutoid.biz.member.domain.Member;
import kr.itycoon.plutoid.biz.member.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;


    @Test
    public void memberInsertTest() {
        Member member = Member.builder()
                              .memberId("d111e145-e54d-4833-b85c-7e141fb6f162")
                              .memberName("연주황")
                              .email("babyOrange@itycoon.kr")
                              .password("$2a$10$vOs41gAO9.mPffb/azYQteLRksXX9Cf6ots9z1nP/zDOlcot4ynUi")
                              .memberRole(MemberRoleEnum.MEMBER)
                              .joinDate(new Date())
                              .photoUrl("")
                              .activeYn("Y")
                              .deleteYn("N")
                              .deleteDate(null)
                              .build();
        int cnt = memberMapper.mergeMember(member);

        assertTrue(cnt == 1);


    }
}
