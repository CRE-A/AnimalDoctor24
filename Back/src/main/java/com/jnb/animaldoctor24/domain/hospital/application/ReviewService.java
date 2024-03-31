package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.dto.ReviewResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    public ResponseEntity<String> register(ReviewResponse request);

    public ResponseEntity<String> modify(ReviewResponse request);

    public ResponseEntity<String> delete(ReviewResponse request);
}