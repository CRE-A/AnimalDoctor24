package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HospitalService {

    public List<Hospital> list();

    public Hospital getHospital(Integer hn);

    public ResponseEntity<String> register(HospitalRequest request);

    public ResponseEntity<String> modify(HospitalRequest request);

    public ResponseEntity<String> delete(HospitalRequest request);
}
