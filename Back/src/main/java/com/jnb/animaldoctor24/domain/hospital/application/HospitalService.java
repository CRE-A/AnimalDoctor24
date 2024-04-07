package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HospitalService {

    public List<Hospital> list();
//    public List<Hospital> listByEmail(String eamil);

    public Hospital getHospital(Long hn);
//    public Hospital getHospitalByEmail(Long hn, String email);

    public ResponseEntity<String> register(HospitalRegisterRequest request);

    public ResponseEntity<String> modify(HospitalModifyRequest request, Long hn);

    public ResponseEntity<String> delete(Long hn);
}
