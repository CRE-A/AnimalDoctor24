package com.jnb.animaldoctor24.domain.hospital.error.exception;

public class HospitalRegisterException extends RuntimeException {
    public HospitalRegisterException(String message) {super((message));}
    public HospitalRegisterException(String message, Throwable cause){super(message,cause);}
}
