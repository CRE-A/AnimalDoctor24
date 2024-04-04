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
public class HospitalModifyRequest {
    @Schema(description = "등록자 이메일", example = "jagure1991@naver.com")
    private String email;
    @Schema(description = "등록자 권한", example = "ADMIN")
    private String role;
    @Schema(description = "병원이름", example = "김길수 동물병원")
    private String hospitalName;
    @Schema(description = "병원 위치", example = "서울특별시 동대문구 청계천로")
    private String location;
    @Schema(description = "소개", example = "고양이만 진료하는 김길수 동물병원입니다. 저희 병원은 종로 5가에 위치하고 있습니다. 고양이가 더 편안하게 다녀갈 수 있는 공간이 되고자 노력하겠습니다.")
    private String description;
    @Schema(description = "진료시간 안내(요일)", example = "월,화,수,목,금")
    private String businessDay;
    @Schema(description = "진료시간 안내(시간)", example = "10:00~19:00")
    private String businessHour;
    @Schema(description = "점심시간", example = "12:00~13:00")
    private String lunchHour;
    @Schema(description = "병원 전화번호", example = "02-2677-2720")
    private String hospitalPhoneNumber;
    @Schema(description = "이미지경로", example = "/path/asdf/1234")
    private String imagePath;
}
