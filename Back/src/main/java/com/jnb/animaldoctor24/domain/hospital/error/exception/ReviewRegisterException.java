package com.jnb.animaldoctor24.domain.hospital.error.exception;

public class ReviewRegisterException extends RuntimeException {
    public ReviewRegisterException(String message) {super((message));}
    public ReviewRegisterException(String message, Throwable cause){super(message,cause);}
}
