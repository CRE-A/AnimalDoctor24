package com.jnb.animaldoctor24.global.error;

import com.jnb.animaldoctor24.domain.hospital.error.exception.HospitalDeleteException;
import com.jnb.animaldoctor24.domain.hospital.error.exception.HospitalModifyException;
import com.jnb.animaldoctor24.domain.hospital.error.exception.HospitalRegisterException;
import com.jnb.animaldoctor24.global.error.exception.ConflictException;
import com.jnb.animaldoctor24.global.error.exception.DataAlreadyExistException;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
     * RuntimeException :  local 에서 처리하지 못한 예외 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse("RuntimeException", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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
     * DataNotFoundException : DB 조회 결과 값이 null 인 경우 예외 처리
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> dataNotFoundException(DataNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("dataNotFoundException", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }


    /*
     * DataNotFoundException : DB 조회 결과 값이 DATA가 이미 존재하는 경우 예외 처리
     */
    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> DataAlreadyExistException(DataAlreadyExistException e) {
        ErrorResponse errorResponse = new ErrorResponse("DataAlreadyExistException", e.getMessage());
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


    /*
     * HospitalRegisterException : 병원 등록 중 오류가 난 경우
     */
    @ExceptionHandler(HospitalRegisterException.class)
    public ResponseEntity<ErrorResponse> HospitalRegisterException(HospitalRegisterException e) {
        ErrorResponse errorResponse = new ErrorResponse("HospitalRegisterException", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     * HospitalModifyException : 병원 수정 중 오류가 난 경우
     */
    @ExceptionHandler(HospitalModifyException.class)
    public ResponseEntity<ErrorResponse> HospitalModifyException(HospitalModifyException e) {
        ErrorResponse errorResponse = new ErrorResponse("HospitalModifyException", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*
     * HospitalDeleteException : 병원 수정 중 오류가 난 경우
     */
    @ExceptionHandler(HospitalDeleteException.class)
    public ResponseEntity<ErrorResponse> HospitalDeleteException(HospitalDeleteException e) {
        ErrorResponse errorResponse = new ErrorResponse("HospitalDeleteException", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
