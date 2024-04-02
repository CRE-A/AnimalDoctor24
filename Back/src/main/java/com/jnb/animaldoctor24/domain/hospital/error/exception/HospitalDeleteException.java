package com.jnb.animaldoctor24.domain.hospital.error.exception;

import lombok.Getter;

@Getter
public class HospitalDeleteException extends RuntimeException {
    public HospitalDeleteException(String message) {super((message));}
    public HospitalDeleteException(String message, Throwable cause){super(message,cause);}
}
