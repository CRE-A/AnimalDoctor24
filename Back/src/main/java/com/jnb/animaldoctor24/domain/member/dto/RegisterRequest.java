package com.jnb.animaldoctor24.domain.member.dto;

import com.jnb.animaldoctor24.domain.member.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty
    @Schema(description = "이름", example = "혜빈")
    private String firstName;
    @NotEmpty
    @Schema(description = "성", example = "홍")
    private String lastName;
    @NotEmpty
    @Schema(description = "이메일", example = "hello@naver.com")
    private String email;
    @NotEmpty
    @Schema(description = "비밀번호", example = "jaeHiddelStone!")
    private String password;
    @NotEmpty
    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;
    @NotEmpty
    private Role role;
    @NotEmpty
    @Schema(description = "반려동물 이름", example = "크레아")
    private String animalName;
    @NotEmpty
    @Schema(description = "반려동물 성별", example = "남아/여아")
    private String animalGender;
    @NotEmpty
    @Schema(description = "반려동물 품종", example = "러시안블루")
    private String animalBreed;
    @NotEmpty
    @Schema(description = "이미지경로", example = "/path/asdf/1234")
    private String imagePath;



}
