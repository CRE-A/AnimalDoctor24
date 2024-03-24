package kr.itycoon.plutoid.biz.member.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import kr.itycoon.plutoid.biz.member.domain.EmailCheck;
import kr.itycoon.plutoid.biz.member.mapper.EmailCheckMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailCheckService {

    private final EmailCheckMapper emailCheckMapper;

    // 이메일 확인 생성
    public int createEmailCheck(EmailCheck emailCheck) {
        final Date now = new Date(); // 현재 시간
        long threeHoursInMillis = 3 * 60 * 60 * 1000; // 3시간을 밀리초로 표현
        Date expireDate = new Date(now.getTime() + threeHoursInMillis); // 3시간 더하기
        EmailCheck.builder()
                  .issueDate(now)
                  .expireDate(expireDate)
                  .build();
        return emailCheckMapper.insert(emailCheck);
    }

}
