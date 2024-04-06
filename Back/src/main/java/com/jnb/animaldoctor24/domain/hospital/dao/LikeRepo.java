package com.jnb.animaldoctor24.domain.hospital.dao;

import com.jnb.animaldoctor24.domain.hospital.domain.Hospital;
import com.jnb.animaldoctor24.domain.hospital.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepo extends JpaRepository<Like, Long> {
    Optional<Like> findByHnAndEmail(Long hn, String email);
    Optional<Like> findByLnAndEmail(Long ln, String email);
//    Optional<Like> findAllByHnAndEmail(Long hn, String email);
//
//    List<Like> findAllByEmail(String email);

}
