package com.jnb.animaldoctor24.domain.member.api;


import com.jnb.animaldoctor24.domain.member.application.AuthenticationService;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationRequest;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationResponse;
import com.jnb.animaldoctor24.domain.member.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name="로그인/회원가입", description="로그인 API")
public class MemberController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "신규가입")
    public ResponseEntity<ResponseEntity<String>> signup(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "로그인", description = "authentication/authorization")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PutMapping("/re-authenticate/{email}")
    @Operation(summary = "계정정보 변경", description = "아이디 혹은 패스워드 바꿀 때")
    public ResponseEntity<AuthenticationResponse> reAuthenticate(@PathVariable String email, @Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.reAuthenticate(request, email));
    }

    @PutMapping("/withdraw/{email}")
    @Operation(summary = "계정 삭제", description = "계정 삭제. 단 정보는 일정기간 DB에")
    public ResponseEntity<?> withdraw(@PathVariable(name = "email") String email){
        authenticationService.withdraw(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/v1/auth/logout"));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}
