package com.jnb.animaldoctor24.domain.member.api;

import com.jnb.animaldoctor24.domain.member.application.MemberService;
import com.jnb.animaldoctor24.domain.member.application.impl.MemberServiceImpl;
import com.jnb.animaldoctor24.domain.member.domain.Member;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationRequest;
import com.jnb.animaldoctor24.domain.member.dto.TokenResponse;
import com.jnb.animaldoctor24.global.common.CommonData;
import com.jnb.animaldoctor24.global.common.CommonDataHolder;
import com.jnb.animaldoctor24.global.common.CommonResult;
import com.jnb.animaldoctor24.global.config.security.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(LoginController.class);
    private final MemberService memberService;
    private final JwtService jwtService;



    @PostMapping("/login")
    @Operation(summary = "로그인", description = "authentication/authorization")
    public ResponseEntity<CommonResult<TokenResponse>> login(HttpServletResponse response) {

        CommonData commonData = CommonDataHolder.getCommonData();

        log.debug("common data : {} ", commonData);

        if (commonData == null) {
            throw new AuthenticationCredentialsNotFoundException("토큰이 유효하지 않습니다.");
        }

        String accessToken = jwtService.generateAccessToken(commonData.getEmail(), commonData.getRole());
        String refreshToken = jwtService.generateRefreshToken(commonData.getEmail());

        Member member = memberService.findMemberByEmail(commonData.getEmail());


        TokenResponse result = new TokenResponse(
                member.getEmail(),
                member.getFirstName(),
                member.getImagePath(),
                accessToken);

        setCookie(response, "_rtkn", refreshToken);

        return ResponseEntity.ok(new CommonResult<>(result));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> payload) {
        log.debug("payload : {} ", payload);

        return ResponseEntity.ok(new CommonResult<>());
    }

    @PostMapping("/token")
    public ResponseEntity<CommonResult<TokenResponse>> refreshAccessToken(HttpServletResponse response, @CookieValue(value = "_rtkn") String refreshToken) {

        log.debug("refreshToken : {}", refreshToken);
        if (refreshToken == null || refreshToken.trim()
                .isEmpty()) {
            throw new IllegalArgumentException("권한없는 회원의 요청입니다.");
        }

        // 토큰에서 pk로 회원정보조회
        Map<String, String> tokenData = jwtService.parseToken(refreshToken);
        String KEY_EMAIL = tokenData.get(JwtService.ClaimConstant.KEY_EMAIL);
        Member member = memberService.findMemberByEmail(KEY_EMAIL);

        // 새로운 액세스 토큰 발급
        String newAccessToken = jwtService.generateAccessToken(member.getEmail(), member.getRole());
        TokenResponse result = new TokenResponse(
                member.getEmail(),
                member.getFirstName(),
                member.getImagePath(),
                newAccessToken);

        log.debug("새로운 엑세스 토큰 발급 ->  TokenResponseDto - result : {} ", result);

        return ResponseEntity.ok(new CommonResult<>(result));
    }




    private void setCookie(HttpServletResponse response, String key, String value) {
        setCookie(response, key, value, "Strict", true, true, null);
    }

    private void setCookie(HttpServletResponse response, String key, String value, String sameSite, boolean isSecure, boolean isHttpOnly, Long maxAge) {

        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(key, value)
                .path("/")
                .sameSite(sameSite)
                .secure(isSecure)
                .httpOnly(isHttpOnly);
        if (maxAge != null) {
            cookieBuilder.maxAge(maxAge);
        }
        response.addHeader(HttpHeaders.SET_COOKIE, cookieBuilder.build()
                .toString());
    }

}
