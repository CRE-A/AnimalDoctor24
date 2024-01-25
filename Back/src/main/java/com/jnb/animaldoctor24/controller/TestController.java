package com.jnb.animaldoctor24.controller;


import com.jnb.animaldoctor24.common.CommonResult;
import com.jnb.animaldoctor24.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Log4j2
public class TestController {


    @PostMapping("/test")
    public ResponseEntity<CommonResult> test(@Valid @RequestBody UserDto userDto) {

        return new ResponseEntity<CommonResult>(new CommonResult(userDto), HttpStatus.OK);
    }
}
