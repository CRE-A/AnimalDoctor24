package com.jnb.animaldoctor24.global.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class CommonResult<T> {
    private Boolean isSuccess;
    private T result;

    public CommonResult(T result) {
        this.isSuccess = true;
        this.result = result;
    }

}
