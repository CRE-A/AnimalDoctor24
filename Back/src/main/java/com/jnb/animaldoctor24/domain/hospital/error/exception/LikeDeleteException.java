package com.jnb.animaldoctor24.domain.hospital.error.exception;

public class LikeDeleteException extends RuntimeException {
    public LikeDeleteException(String message) {super((message));}
    public LikeDeleteException(String message, Throwable cause){super(message,cause);}
}
