package com.jnb.animaldoctor24.domain.member.application;

import com.jnb.animaldoctor24.domain.member.dto.*;
import com.jnb.animaldoctor24.global.common.CommonResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public CommonResult<TokenResponse> login(HttpServletResponse response);
    public CommonResult<TokenResponse> refreshAccessToken(HttpServletResponse response, String refreshToken);

    ResponseEntity<String> register(MemberRegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response);

    ResponseEntity<String> reAuthenticate(MemberModiyRequest request, String email);

    void withdraw(String email);
}
