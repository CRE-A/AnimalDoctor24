package kr.itycoon.plutoid.biz.member.dto;

import java.util.Date;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
public class MemberRequestDto {

    @NotNull
    private String memberName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirm;

    @NotNull
    private String checkCode;

    @Builder
    public MemberRequestDto(String memberName, String email, String password, String passwordConfirm, String checkCode) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.checkCode = checkCode;
    }

    public void encodingPassword(PasswordEncoder passwordEncoder) {
        if (password == null || password.isEmpty()) {
            return;
        }
        password = passwordEncoder.encode(password);
    }
}
