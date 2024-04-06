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
public class HospitalResponse {
    private Integer hn;
    private String email;
    private String role;
    private String hospitalName;
    private String location;
    private String description;
    private String businessDay;
    private String businessHour;
    private String lunchHour;
    private String hospitalPhoneNumber;
    private String imagePath;
    private Data creationDate;
    private Data updateDate;
    private String ln;



}
