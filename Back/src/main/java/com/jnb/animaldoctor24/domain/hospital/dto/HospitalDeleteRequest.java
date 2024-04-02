package com.jnb.animaldoctor24.domain.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDeleteRequest {
    @Schema(description = "병원고유번호", example = "21")
    private String hn;
    @Schema(description = "이메일", example = "jagure1991@naver.com")
    private String email;
    @Schema(description = "권한", example = "USER/ADMIN")
    private String role;
    @Schema(description = "병원이름", example = "김길수 동물병원")
    private String name;
}
