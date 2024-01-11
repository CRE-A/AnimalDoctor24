package com.jnb.animaldoctor24.common;


import lombok.Getter;

@Getter
public class ErrorResponse{

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


