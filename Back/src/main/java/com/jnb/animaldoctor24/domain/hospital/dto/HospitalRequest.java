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
public class HospitalRequest {
//    private Integer hn; // hospital number

    @Schema(description = "이메일", example = "jagure1991@naver.com")
    private String email;
    @Schema(description = "권한", example = "USER/ADMIN")
    private String role;
    @Schema(description = "병원이름", example = "김길수 동물병원")
    private String name;
    @Schema(description = "병원정보", example = "동대문구 신설동에 위치한 고양이, 강아지 전문 진료 병원")
    private String contents;
    @Schema(description = "태그", example = "신설동, 강아지")
    private String tag;
    @Schema(description = "이미지경로", example = "/path/asdf/1234")
    private String imgPath;

}
