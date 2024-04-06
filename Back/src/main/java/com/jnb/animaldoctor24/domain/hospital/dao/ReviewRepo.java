package com.jnb.animaldoctor24.domain.hospital.dao;

import com.jnb.animaldoctor24.domain.hospital.domain.Review;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findAllByHn(Long hn);
    Optional<Review> findByRn(Long rn);

}
