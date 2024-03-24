package kr.itycoon.plutoid.biz.member.dto;

import jakarta.validation.constraints.NotNull;
import kr.itycoon.plutoid.biz.common.model.IssueForEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@ToString
public class NonLoginMember {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirm;

    @NotNull
    private String checkCode;

    @Builder
    public NonLoginMember(String email, String password, String passwordConfirm, String checkCode) {
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
