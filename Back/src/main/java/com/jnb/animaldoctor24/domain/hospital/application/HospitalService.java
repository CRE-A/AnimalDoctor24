package com.jnb.animaldoctor24.domain.hospital.application;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalModifyRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRegisterRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HospitalService {

    public List<Hospital> list();
    public List<HospitalResponse> listByEmail(String eamil);

    public Hospital getHospital(Integer hn);
    public HospitalResponse getHospitalByEmail(Integer hn, String email);

    public ResponseEntity<String> register(HospitalRegisterRequest request);

    public ResponseEntity<String> modify(HospitalModifyRequest request, Integer hn);

    public ResponseEntity<String> delete(Integer hn);
}
