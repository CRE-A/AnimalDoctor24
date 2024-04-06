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

public class ReviewRegisterRequest {
    @Schema(description = "hospital number", example = "231")
    private String hn;
    @Schema(description = "등록자 이메일", example = "jagure1991@naver.com")
    private String email;
    @Schema(description = "등록자 권한", example = "ADMIN")
    private String role;
    @Schema(description = "리뷰 제목", example = "왕맛있어요")
    private String title;
    @Schema(description = "리뷰 본문", example = "라고할줄알았냐? 돈버렸다 이놈아. 다시는 안시켜먹는다")
    private String contents;
    @Schema(description = "이미지경로", example = "/path/asdf/1234")
    private String imagePath;

}
