package com.jnb.animaldoctor24.global.error;

import com.jnb.animaldoctor24.global.error.exception.BizException;
import com.jnb.animaldoctor24.global.error.exception.ConflictException;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*
     * Exception : 자손 Exception class에서 처리하지 못하는 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("Exception", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     * Exception : 자손 Exception class에서 처리하지 못하는 예외 처리
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> dataNotFoundException(DataNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("DataNotFoundException", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /*
     * BizException : 서비스 단계에서 던지는 예외를 처리
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResponse> handleException(BizException e) {
        ErrorResponse errorResponse = new ErrorResponse("Bizexception", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
     * BadRequestException : 사용자 요청 값에 문제가 있을 경우 사용
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse("Bad Request", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
     * UnAuthorizedException : 사용자 인증처리에 문제가 있을 경우 사용
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleException(AuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse("AuthenticationException", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /*
     * Conflict : 중복값이 들어온 경우 사용
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleException(ConflictException e) {
        ErrorResponse errorResponse = new ErrorResponse("Conflict", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }

    /*
     * ExpiredToken : 토큰이 만료된 경우
     */
    @ExceptionHandler(NonceExpiredException.class)
    public ResponseEntity<ErrorResponse> handleException(NonceExpiredException e) {
        ErrorResponse errorResponse = new ErrorResponse("AccessToken Expired", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
