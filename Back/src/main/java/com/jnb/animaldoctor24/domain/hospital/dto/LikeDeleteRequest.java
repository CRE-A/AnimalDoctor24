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
public class LikeDeleteRequest {
    @Schema(description = "hospital number", example = "31")
    private  Long hn;
    @Schema(description = "유저 이메일", example = "jagure1991@naver.com")
    private String eamil;
}
