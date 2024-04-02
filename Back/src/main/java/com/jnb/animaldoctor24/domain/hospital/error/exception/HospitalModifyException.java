package com.jnb.animaldoctor24.domain.hospital.error.exception;

public class HospitalModifyException extends RuntimeException{
    public HospitalModifyException(String message) {super((message));}
    public HospitalModifyException(String message, Throwable cause){super(message,cause);}
}
