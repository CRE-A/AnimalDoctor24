package com.jnb.animaldoctor24.domain.member.application;

import com.jnb.animaldoctor24.domain.member.dto.AuthenticationRequest;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationResponse;
import com.jnb.animaldoctor24.domain.member.dto.MemberModiyRequest;
import com.jnb.animaldoctor24.domain.member.dto.MemberRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<String> register(MemberRegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    ResponseEntity<String> reAuthenticate(MemberModiyRequest request, String email);

    void withdraw(String email);
}
