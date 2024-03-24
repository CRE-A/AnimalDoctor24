package kr.itycoon.plutoid.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ConflictException extends RuntimeException {   

    public ConflictException() {
        super();
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
  
}
