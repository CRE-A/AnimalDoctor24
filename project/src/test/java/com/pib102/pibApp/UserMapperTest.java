package com.pib102.pibApp;

import com.pib102.pibApp.dto.UserDto;
import com.pib102.pibApp.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void insertTest() {
        UserDto user = UserDto.builder()
                .id("비나")
                .pwd("1234")
                .name("홍혜빈")
                .email("bina@itycoon.kr")
                .dpt("devOps")
                .phone("01080218815")
                .build();
        log.debug("user : {}");
        int cnt = userMapper.insertUser(user);


        assertTrue(cnt == 1);

    }

    @Test
    public void selectTest() {
        UserDto user = userMapper.findUserById("crea");
        log.debug("user : {}");

    }


}
