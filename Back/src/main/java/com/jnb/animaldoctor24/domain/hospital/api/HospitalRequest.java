package com.jnb.animaldoctor24.domain.hospital.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalRequest {
    private Integer bn;
    private String id;
    private String role;
    private String title;
    private String contents;
    private String tag;
    private String desc;
    private String imgPath;
}
