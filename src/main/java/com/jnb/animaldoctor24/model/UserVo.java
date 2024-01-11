package com.jnb.animaldoctor24.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
public class UserVo {

    @NonNull
    private String name;
    @NonNull
    private String email;

}
