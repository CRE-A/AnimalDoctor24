package com.jnb.animaldoctor24.domain.hospital.api;

import com.jnb.animaldoctor24.domain.hospital.application.HospitalService;
import com.jnb.animaldoctor24.domain.hospital.application.LikeService;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
@Tag(name="동물병원 하트", description="동물병원 하트(좋아요, 담기) API")
public class LikeController {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;


    @PostMapping("/{hn}/{email}")
    @Operation(summary = "병원 하트 추가", description = "병원 하트(좋아요) 추가")
    public ResponseEntity<ResponseEntity<String>> addLike (@PathVariable Integer hn, @PathVariable String email) {
        return ResponseEntity.ok(likeService.add(hn, email));
    }

    @DeleteMapping("/{ln}/{email}")
    @Operation(summary = "병원 하트 삭제", description = "병원 하트(좋아요) 삭제")
    public ResponseEntity<ResponseEntity<String>> deleteLike (@PathVariable Integer ln, @PathVariable String email) {
        return ResponseEntity.ok(likeService.delete(ln, email));
    }




}
