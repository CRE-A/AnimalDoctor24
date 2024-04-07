package com.jnb.animaldoctor24.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String email;

    private String firstname;

    private String imgPath;

    private String accessToken;
}
