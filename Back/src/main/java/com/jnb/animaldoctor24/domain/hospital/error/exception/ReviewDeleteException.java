package com.jnb.animaldoctor24.domain.hospital.error.exception;

public class ReviewDeleteException extends RuntimeException{
    public ReviewDeleteException(String message) {super((message));}
    public ReviewDeleteException(String message, Throwable cause){super(message,cause);}
}
