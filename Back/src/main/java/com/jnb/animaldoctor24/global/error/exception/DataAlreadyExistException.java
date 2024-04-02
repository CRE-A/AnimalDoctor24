package com.jnb.animaldoctor24.global.error.exception;

import lombok.Getter;

@Getter
public class DataAlreadyExistException extends RuntimeException{
    public DataAlreadyExistException(String message){super((message));}
    public DataAlreadyExistException(String message, Throwable cause){super(message,cause);}
}
