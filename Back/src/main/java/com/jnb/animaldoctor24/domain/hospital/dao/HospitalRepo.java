package com.jnb.animaldoctor24.domain.hospital.dao;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepo extends JpaRepository<Hospital, Integer> {
    List<Hospital> findAllBy();
//    Hospital findByHn(Integer hn);
    Optional<Hospital> findHospitalByName(String name);

    Optional<Hospital> findByHn(Integer hn);

//    List<HospitalDto> findAllBy(Integer page);
}
