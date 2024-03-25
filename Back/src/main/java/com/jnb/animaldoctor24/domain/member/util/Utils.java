package com.jnb.animaldoctor24.domain.member.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {

    private Utils() {

    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage , HttpStatus httpStatus) {
        return new ResponseEntity<String>(responseMessage ,httpStatus);
    }
}