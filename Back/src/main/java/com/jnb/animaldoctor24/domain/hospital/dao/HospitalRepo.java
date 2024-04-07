package com.jnb.animaldoctor24.domain.hospital.dao;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepo extends JpaRepository<Hospital, Long> {


    Optional<Hospital> findHospitalByHospitalName(String hospitalName);

    Optional<Hospital> findByHn(Long hn);
    Optional<Hospital> findByHnAndEmail(Long hn, String email);
    Optional<Hospital> findAllByEmail(String email);

    List<Hospital> findByLike_Email(String email);
    Optional<Hospital> findByLike_HnAndEmail(Long hn, String email);
    Optional<Hospital> findByLike_HnAndLike_Email(Long hn, String email);




}
