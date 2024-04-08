package com.jnb.animaldoctor24.domain.member.application;

import com.jnb.animaldoctor24.domain.member.domain.Member;
import com.jnb.animaldoctor24.domain.member.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface MemberService {

    ResponseEntity<String> register(MemberRegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response);

    ResponseEntity<String> reAuthenticate(MemberModiyRequest request, String email);

    public Member findMemberByEmail(String email);

    void withdraw(String email);
}
