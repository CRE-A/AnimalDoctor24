package com.jnb.animaldoctor24.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
        @NotEmpty
        @Schema(description = "이메일", example = "hello@naver.com")
        private String email;
        @NotEmpty
        @Schema(description = "비밀번호", example = "jaeHiddelStone!")
        private String password;
}
