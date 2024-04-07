package com.jnb.animaldoctor24.domain.hospital.api;

import com.jnb.animaldoctor24.domain.hospital.application.LikeService;
import com.jnb.animaldoctor24.domain.hospital.dto.LikeDeleteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 병원 좋아요(하트) 등록/삭제
@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
@Slf4j
@Tag(name="동물병원 하트", description="동물병원 하트(좋아요, 담기) API")
public class LikeController {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;


    @PostMapping("/{hn}/{email}")
    @Operation(summary = "병원 하트 추가", description = "병원 하트(좋아요) 추가")
    public ResponseEntity<ResponseEntity<String>> addLike (@PathVariable Long hn, @PathVariable String email) {
        return ResponseEntity.ok(likeService.add(hn, email));
    }

    @DeleteMapping("/{ln}")
    @Operation(summary = "병원 하트 삭제", description = "병원 하트(좋아요) 삭제")
    public ResponseEntity<ResponseEntity<String>> deleteLike (@PathVariable Long ln, @Valid @RequestBody LikeDeleteRequest request) {
        return ResponseEntity.ok(likeService.delete(ln, request));
    }




}
