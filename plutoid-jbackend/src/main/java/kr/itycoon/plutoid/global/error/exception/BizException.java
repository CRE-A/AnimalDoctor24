package kr.itycoon.plutoid.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
