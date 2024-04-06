package com.jnb.animaldoctor24.domain.hospital.dao;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.dto.HospitalResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepo extends JpaRepository<Hospital, Integer> {


    Optional<Hospital> findHospitalByHospitalName(String hospitalName);

    Optional<Hospital> findByHn(Integer hn);


}
