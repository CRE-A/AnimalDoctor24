package kr.itycoon.plutoid.biz.member.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import kr.itycoon.plutoid.global.error.ErrorResponse;

@RestController
@RequestMapping("/api/v1/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public ResponseEntity<ErrorResponse> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return ResponseEntity.status(status.value()).body(new ErrorResponse(status.name(),status.getReasonPhrase()));
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
