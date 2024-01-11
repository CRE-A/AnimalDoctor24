package com.jnb.animaldoctor24.controller;


import com.jagure.butterfly.common.CommonResult;
import com.jagure.butterfly.model.UserVo;
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
    public ResponseEntity<CommonResult> test(@Valid @RequestBody UserVo user) {
        log.debug("user : {}", user);
        return new ResponseEntity<CommonResult>(new CommonResult(user), HttpStatus.OK);
    }
}
