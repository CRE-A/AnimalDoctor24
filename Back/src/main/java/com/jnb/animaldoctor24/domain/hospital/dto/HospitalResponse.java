package com.jnb.animaldoctor24.domain.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalResponse {
    private Integer hn; // hospital number
    private String email;
    private String role;
    private String title;
    private String contents;
    private String tag;
    private String desc;
    private String imgPath;

}
