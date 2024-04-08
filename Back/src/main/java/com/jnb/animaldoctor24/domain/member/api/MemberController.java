package com.jnb.animaldoctor24.domain.member.api;


import com.jnb.animaldoctor24.domain.member.application.impl.MemberServiceImpl;
import com.jnb.animaldoctor24.domain.member.dto.*;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

// 인증 / 인가
// 맴버 등록/수정/삭제
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
@Tag(name="로그인/회원가입", description="로그인 API")
public class MemberController {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberServiceImpl authenticationService;


//    @GetMapping("/login")
//    @Operation(summary = "로그인 화면", description = "로그인 폼")
//    public ResponseEntity<?> login() {
//        return null;
//    }


    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "신규가입")
    public ResponseEntity<ResponseEntity<String>> signup(@Valid @RequestBody MemberRegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "로그인", description = "authentication/authorization")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.authenticate(request, response));
    }

    @PutMapping("/re-authenticate/{email}")
    @Operation(summary = "계정정보 변경", description = "아이디 혹은 패스워드 바꿀 때")
    public ResponseEntity<ResponseEntity<String>> reAuthenticate(@PathVariable String email, @Valid @RequestBody MemberModiyRequest request) {
        return ResponseEntity.ok(authenticationService.reAuthenticate(request, email));
    }

    @PutMapping("/withdraw/{email}")
    @Operation(summary = "계정 삭제", description = "계정 삭제. 단 정보는 일정기간 DB에")
    public ResponseEntity<?> withdraw(@PathVariable(name = "email") String email){
        authenticationService.withdraw(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/v1/auth/logout"));
        headers.set("msg", ResponseConstants.USER_DELETE_SUCCESS);
        return new ResponseEntity<>(headers,  HttpStatus.MOVED_PERMANENTLY);
    }


//    @GetMapping("/logout")
//    @Operation(summary = "로그인 화면", description = "로그인 폼")
//    public ResponseEntity logout(HttpServletRequest request) {
////        String encryptedRefreshToken = jwtTokenProvider.resolveRefreshToken(request);
////        String accessToken = jwtTokenProvider.resolveAccessToken(request);
////        authService.logout(encryptedRefreshToken, accessToken);
////
////        return new ResponseEntity<>(new SingleResponseDto<>("Logged out successfully"), HttpStatus.NO_CONTENT);
//        return null;
//    }


}
