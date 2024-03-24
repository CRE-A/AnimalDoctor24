package kr.itycoon.plutoid.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kr.itycoon.plutoid.global.error.ErrorResponse.ErrorDetails;
import kr.itycoon.plutoid.global.error.exception.BizException;
import kr.itycoon.plutoid.global.error.exception.ConflictException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger Logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*
     * Exception : 자손 Exception class에서 처리하지 못하는 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("Exception", e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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


//    /*
//     * RuntimeException 처리
//     */
//    @ExceptionHandler(RestApiException.class)
//    protected ResponseEntity<ErrorResponse> handleCustomException(RestApiException e) {
//        log.error("handleConflictException", e);
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response= new ErrorResponse(errorCode);
//        return new ResponseEntity<>(response, errorCode.getHttpStatus());
//    };
//
//    /*
//     * Exception : 자손 Exception class에서 처리하지 못하는 예외 처리
//     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
//        log.error("handleBadRequestException", e);
//        final ErrorResponse response= new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(response, ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus());
//    }
//
//    /*
//     * BadRequestException : 사용자 요청 값에 문제가 있을 경우 사용
//     */
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleBadRequestException (IllegalArgumentException e){
//        log.error("handleBadRequestException", e);
//        final ErrorResponse response= new ErrorResponse(ErrorCode.BAD_REQUEST);
//        return new ResponseEntity<>(response, ErrorCode.BAD_REQUEST.getHttpStatus());
//    }
//
//    /*
//     * UnAuthorizedException : 사용자 인증처리에 문제가 있을 경우 사용
//     */
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ErrorResponse> handleUnAuthorizedException(AuthenticationException e) {
//        log.error("handleConflictException", e);
//        final ErrorResponse response= new ErrorResponse(ErrorCode.UNAUTHORIZED_REQUEST);
//        return new ResponseEntity<>(response, ErrorCode.UNAUTHORIZED_REQUEST.getHttpStatus());
//    }
//
//    /*
//     * ForbiddenException : 사용자 인가처리에 문제가 있을 경우 사용
//     */
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorResponse> handleForbiddenException(AccessDeniedException e) {
//        log.error("handleConflictException", e);
//        final ErrorResponse response= new ErrorResponse(ErrorCode.FORBIDDEN_ACCESS);
//        return new ResponseEntity<>(response, ErrorCode.FORBIDDEN_ACCESS.getHttpStatus());
//    }
//
//    /*
//     * ConflictException : 중복허용을 하지 않는데 데이터 중복이 발생하는 경우 사용
//     */
//    @ExceptionHandler(ConflictException.class)
//    public ResponseEntity<ErrorResponse> handleConflictException (ConflictException e){
//        log.error("handleConflictException", e);
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response= new ErrorResponse(errorCode);
//        return new ResponseEntity<>(response, errorCode.getHttpStatus());
//    }

}
