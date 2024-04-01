package com.jnb.animaldoctor24.global.error.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
  
}
