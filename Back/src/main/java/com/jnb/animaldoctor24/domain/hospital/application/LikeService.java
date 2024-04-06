package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.ReviewRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeService {
public ResponseEntity<String> add(Integer hn, String eamil);

    public ResponseEntity<String> delete(Integer rn, String eamil);

}
