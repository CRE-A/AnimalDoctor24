package com.jnb.animaldoctor24.common;

import lombok.Getter;

public class ErrorResponse {

    // 에러응답기 11 22
    private final Boolean isSuccess;
    private final ErrorDetails error;

    public ErrorResponse(String name, String message) {
        this.isSuccess = false;
        this.error = new ErrorDetails(name, message);
    }

    @Getter
    class ErrorDetails {
        private String name;
        private String message;

        public ErrorDetails(String name, String message) {
            this.name = name;
            this.message = message;
        }
    }

}
