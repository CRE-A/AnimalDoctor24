package com.jnb.animaldoctor24.domain.hospital.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Integer rn; // review number
    private Integer hn; // hospital number
    private String id;
    private String role;
    private String title;
    private String contents;
    private String imgPath;
}
