package com.jnb.animaldoctor24.domain.member.dao;

import com.jnb.animaldoctor24.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);

}
