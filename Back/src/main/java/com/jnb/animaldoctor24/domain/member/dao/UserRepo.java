package com.jnb.animaldoctor24.domain.member.dao;

import com.jnb.animaldoctor24.domain.member.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
