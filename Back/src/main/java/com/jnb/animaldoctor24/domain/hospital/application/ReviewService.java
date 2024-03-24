package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.api.ReviewRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    public ResponseEntity<String> register(ReviewRequest request);

    public ResponseEntity<String> modify(ReviewRequest request);

    public ResponseEntity<String> delete(ReviewRequest request);
}