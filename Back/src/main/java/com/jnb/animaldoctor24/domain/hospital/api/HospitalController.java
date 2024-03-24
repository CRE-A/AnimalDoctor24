package com.jnb.animaldoctor24.domain.hospital.api;

import com.jnb.animaldoctor24.domain.hospital.application.HospitalService;
import com.jnb.animaldoctor24.domain.member.api.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 병원 등록/삭제   [## 관리자만 ##]
// 병원 좋아요(하트) 등록/삭제
// 병원 검색
@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;


    @PostMapping("/register")
    public ResponseEntity<ResponseEntity<String>> registerHospital (@RequestBody HospitalRequest request) {
        return ResponseEntity.ok(hospitalService.register(request));
    }


    @PostMapping("/modify")
    public ResponseEntity<ResponseEntity<String>> modifyHospital (@RequestBody HospitalRequest request) {
        return ResponseEntity.ok(hospitalService.modify(request));
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseEntity<String>> deleteHospital (@RequestBody HospitalRequest request) {
        return ResponseEntity.ok(hospitalService.delete(request));
    }
}
