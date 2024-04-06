package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.domain.Review;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.ReviewRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    public List<Review> list(Integer hn);

    Review getReview(Integer rn) throws RuntimeException;

    public ResponseEntity<String> register(ReviewRegisterRequest request, Integer hn);

    public ResponseEntity<String> modify(HospitalModifyRequest request, Integer rn);

    public ResponseEntity<String> delete(Integer rn);


}