package com.jnb.animaldoctor24.domain.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private Integer cn;
    private Integer bn;
    private String id;
    private String role;
    private String title;
    private String contents;
    private String imgPath;

}
