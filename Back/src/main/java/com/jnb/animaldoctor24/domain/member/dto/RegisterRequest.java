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
    @Schema(description = "이메일", example = "hello@naver.com")
    private String email;
    @Schema(description = "비밀번호", example = "jaeHiddelStone!")
    private String password;
    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;
    private Role role;


}
