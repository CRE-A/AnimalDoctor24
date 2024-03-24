package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.api.HospitalRequest;
import com.jnb.animaldoctor24.domain.hospital.api.ReviewRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface HospitalService {

    public ResponseEntity<String> register(HospitalRequest request);

    public ResponseEntity<String> modify(HospitalRequest request);

    public ResponseEntity<String> delete(HospitalRequest request);
}
