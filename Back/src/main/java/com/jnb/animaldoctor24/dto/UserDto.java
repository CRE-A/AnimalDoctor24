package com.jnb.animaldoctor24.dto;

import lombok.*;


@Getter
@NoArgsConstructor
@ToString
public class UserDto {
    @NonNull
    private String name;
    private String email;


}
