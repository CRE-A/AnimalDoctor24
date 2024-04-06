package com.jnb.animaldoctor24.domain.hospital.dao;

import com.jnb.animaldoctor24.domain.hospital.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<Like, Integer> {
    Optional<Like> findByHnAndEmail(Integer hn, String email);
    Optional<Like> findByLnAndEmail(Integer ln, String email);

}
