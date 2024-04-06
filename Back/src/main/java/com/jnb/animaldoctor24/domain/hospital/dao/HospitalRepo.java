package com.jnb.animaldoctor24.domain.hospital.dao;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepo extends JpaRepository<Hospital, Integer> {
//    List<Hospital> findAllBy();
    Optional<Hospital> findHospitalByHospitalName(String hospitalName);

    Optional<Hospital> findByHn(Integer hn);

}
