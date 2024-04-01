package com.jnb.animaldoctor24.global.error.exception;
import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(){super();};
    public DataNotFoundException(String message){super(message);}
    public DataNotFoundException(String message, Throwable cause){super(message,cause);}
}
