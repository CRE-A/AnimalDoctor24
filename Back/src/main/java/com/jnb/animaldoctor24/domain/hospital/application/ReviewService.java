package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.domain.Review;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.ReviewModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.ReviewRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    public List<Review> list(Long hn);

    Review getReview(Long rn) throws RuntimeException;

    public ResponseEntity<String> register(ReviewRegisterRequest request, Long hn);

    public ResponseEntity<String> modify(ReviewModifyRequest request, Long rn);

    public ResponseEntity<String> delete(Long rn);


}