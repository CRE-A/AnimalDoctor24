package com.jnb.animaldoctor24.domain.hospital.api;


import com.jnb.animaldoctor24.domain.hospital.application.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 병원 리뷰 등록/수정/삭제
@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Tag(name="동물병원 리뷰", description="동물병원 게시물의 리뷰 API")
public class ReviewController {
    private final ReviewService reviewService;

//    @PostMapping("/register")
//    @Operation(summary = "병원 리스트 조회", description = "병원 목록 조회")
//    public ResponseEntity<ResponseEntity<String>> registerReview (@RequestBody ReviewRequest request) {
//        return ResponseEntity.ok(reviewService.register(request));
//    }
//
//
//    @PostMapping("/modify")
//    public ResponseEntity<ResponseEntity<String>> modifyReview (@RequestBody ReviewRequest request) {
//        return ResponseEntity.ok(reviewService.modify(request));
//    }
//
//    @PostMapping("/delete")
//    public ResponseEntity<ResponseEntity<String>> deleteReview (@RequestBody ReviewRequest request) {
//        return ResponseEntity.ok(reviewService.delete(request));
//    }


}
