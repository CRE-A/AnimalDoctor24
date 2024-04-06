package com.jnb.animaldoctor24.domain.hospital.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Tag(name="동물병원 하트", description="동물병원 하트(좋아요, 담기) API")
public class LikeController {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(LikeController.class);
//    private final

}
