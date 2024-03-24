package com.jnb.animaldoctor24.domain.hospital.api;


import com.jnb.animaldoctor24.domain.hospital.application.ReviewService;
import com.jnb.animaldoctor24.domain.member.api.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 병원 리뷰 등록/수정/삭제
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/register")
    public ResponseEntity<ResponseEntity<String>> registerReview (@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.register(request));
    }


    @PostMapping("/modify")
    public ResponseEntity<ResponseEntity<String>> modifyReview (@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.modify(request));
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseEntity<String>> deleteReview (@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.delete(request));
    }


}
