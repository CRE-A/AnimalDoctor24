package com.JNBproject.controller;


import com.JNBproject.dto.UserDto;
import com.JNBproject.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Log4j2
public class TestController {

    private final UserMapper userMapper;

    @GetMapping("/test")
    public UserDto greeting() {
        UserDto user = userMapper.findUserById("crea");
        log.debug("UserDto : {}", user);
        return user;
    }
}
