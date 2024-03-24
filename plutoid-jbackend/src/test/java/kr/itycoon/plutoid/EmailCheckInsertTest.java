package kr.itycoon.plutoid;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.itycoon.plutoid.biz.common.model.IssueForEnum;
import kr.itycoon.plutoid.biz.member.domain.EmailCheck;
import kr.itycoon.plutoid.biz.member.mapper.EmailCheckMapper;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class EmailCheckInsertTest {
//    this.email = email;
//    this.checkCode = checkCode;
//    this.issueFor = issueFor;
//    this.issueDate = issueDate;
//    this.expireDate = expireDate;

    @Autowired
    EmailCheckMapper emailCheckMapper;

    @Test
    public void insertTest() {

        final Date now = new Date(); // 현재 시간
        long threeHoursInMillis = 3 * 60 * 60 * 1000; // 3시간을 밀리초로 표현
        Date expireDate = new Date(now.getTime() + threeHoursInMillis); // 3시간 더하기

        EmailCheck emailCheck = new EmailCheck("insertTest@itycoon.kr", "123456", IssueForEnum.JOIN, now, expireDate);

        int cnt = emailCheckMapper.insert(emailCheck);
        assertTrue(cnt == 1);

    }


//    @Test
//    public void selectTest() {
//
//        EmailCheckVerifyRequestDto ecvrd = EmailCheckVerifyRequestDto.builder()
//                                                                     .checkCode("123456")
//                                                                     .email("selectTest@itycoon.kr")
//                                                                     .issueFor(IssueForEnum.JOIN)
//                                                                     .build();
//
//        EmailCheck result = emailCheckMapper.select(ecvrd);
//        log.info("select test result : " + ecvrd);
//
//    }

}
