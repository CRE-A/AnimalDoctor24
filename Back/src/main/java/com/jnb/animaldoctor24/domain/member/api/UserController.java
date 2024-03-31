package com.jnb.animaldoctor24.domain.member.api;


import com.jnb.animaldoctor24.domain.member.application.AuthenticationService;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationRequest;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationResponse;
import com.jnb.animaldoctor24.domain.member.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name="로그인/회원가입", description="로그인 API")
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "신규가입")
    public ResponseEntity<ResponseEntity<String>> signup(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "로그인", description = "인증/인가")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

//    @GetMapping("/main")
//    public ResponseEntity<ResponseEntity<String>> home () {
//        System.out.println("/home/main 입장!");
//        return null;
//    }
}
