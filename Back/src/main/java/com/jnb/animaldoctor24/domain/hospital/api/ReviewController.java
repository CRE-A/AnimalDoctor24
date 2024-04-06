package com.jnb.animaldoctor24.domain.hospital.api;


import com.jnb.animaldoctor24.domain.hospital.application.ReviewService;
import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.domain.Review;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRegisterRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.ReviewRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 병원 리뷰 등록/수정/삭제
@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Tag(name="동물병원 리뷰", description="동물병원 리뷰 API")
public class ReviewController {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;

    @GetMapping("/list/{hn}")
    @Operation(summary = "병원리뷰 리스트 조회", description = "병원리뷰 목록 조회")
    public ResponseEntity<List<Review>> showListOfHospital (@PathVariable Integer hn) {
        return ResponseEntity.ok(reviewService.list(hn));
    }

    @GetMapping("/{rn}")
    @Operation(summary = "병원리뷰 조회", description = "병원리뷰 정보 조회")
    public ResponseEntity<Review> showHospital (@PathVariable Integer rn) {
        return ResponseEntity.ok().body(reviewService.getReview(rn));
    }

    @PostMapping("/new/{hn}")
    @Operation(summary = "병원리뷰 정보 등록", description = "병원리뷰 신규 등록")
    public ResponseEntity<ResponseEntity<String>> registerHospital (@PathVariable Integer hn,@Valid @RequestBody ReviewRegisterRequest request) {
        return ResponseEntity.ok(reviewService.register(request, hn));
    }


//    @PatchMapping("/{rn}")
//    @Operation(summary = "병원리뷰 정보 수정", description = "병원리뷰 정보 수정")
//    public ResponseEntity<ResponseEntity<String>> modifyHospital (@PathVariable Integer rn, @RequestBody HospitalModifyRequest request) {
//        return ResponseEntity.ok(reviewService.modify(request, rn));
//    }

    @DeleteMapping("/{rn}")
    @Operation(summary = "병원리뷰 정보 삭제", description = "병원리뷰 정보 삭제")
    public ResponseEntity<ResponseEntity<String>> deleteHospital (@PathVariable Integer rn) {
        return ResponseEntity.ok(reviewService.delete(rn));
    }


}
