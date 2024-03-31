package com.jnb.animaldoctor24.domain.hospital.dao;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalRequest;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalRepo extends JpaRepository<Hospital, Integer> {
    List<Hospital> findAllBy();
    Hospital findByHn(Integer hn);

//    List<HospitalDto> findAllBy(Integer page);
}
