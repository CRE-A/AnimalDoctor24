package com.jnb.animaldoctor24.domain.member.dto;

import com.jnb.animaldoctor24.domain.member.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(description = "이름", example = "혜빈")
    private String firstname;
    @Schema(description = "성", example = "홍")
    private String lastname;
    private String email;
    private String password;
    private String contactNumber;
    private Role role;


}
