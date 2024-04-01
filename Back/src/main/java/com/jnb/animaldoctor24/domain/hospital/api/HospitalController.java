package com.jnb.animaldoctor24.domain.hospital.api;

import com.jnb.animaldoctor24.domain.hospital.application.HospitalService;
import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalResponse;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.zip.DataFormatException;

// 병원 등록/삭제   [## 관리자만 ##]
// 병원 좋아요(하트) 등록/삭제
// 병원 검색
@RestController
@RequestMapping("/api/v1/hospital")
@RequiredArgsConstructor
@Tag(name="동물병원", description="동물병원 API")
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("/list")
    @Operation(summary = "병원 리스트 조회", description = "병원 목록 조회")
    public ResponseEntity<List<Hospital>> showListOfHospital () {
//        if(hospitalService.list()==null){
//            throw new DataNotFoundException("data not found");
//        }
        return ResponseEntity.ok(hospitalService.list());
    }

    @GetMapping("/{hn}")
    @Operation(summary = "병원 조회", description = "병원 정보 조회")
    public ResponseEntity<Hospital> showHospital (@PathVariable Integer hn) {
//        if(hospitalService.getHospital(hn)==null){
//            throw new DataNotFoundException("data not found");
//        }
        return ResponseEntity.ok().body(hospitalService.getHospital(hn));
    }

    @PostMapping("/new")
    @Operation(summary = "병원 정보 등록", description = "병원 신규 등록")
    public ResponseEntity<ResponseEntity<String>> registerHospital (@RequestBody HospitalRequest request) {
        return ResponseEntity.ok(hospitalService.register(request));
    }


    @PatchMapping("/{hn}/{userId}")
    @Operation(summary = "병원 정보 수정", description = "병원 정보 수정")
    public ResponseEntity<ResponseEntity<String>> modifyHospital (@PathVariable Integer hn, @PathVariable String userId, @RequestBody HospitalRequest request) {
        return ResponseEntity.ok(hospitalService.modify(request));
    }

    @DeleteMapping("/{hn}/{userId}")
    @Operation(summary = "병원 정보 삭제", description = "병원 정보 삭제")
    public ResponseEntity<ResponseEntity<String>> deleteHospital (@PathVariable Integer hn, @PathVariable String userId, @RequestBody HospitalRequest request) {
        return ResponseEntity.ok(hospitalService.delete(request));
    }
}
