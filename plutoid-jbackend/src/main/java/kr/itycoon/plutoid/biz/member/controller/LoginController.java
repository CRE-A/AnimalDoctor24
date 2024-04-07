package kr.itycoon.plutoid.biz.member.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletResponse;
import kr.itycoon.plutoid.biz.member.domain.Member;
import kr.itycoon.plutoid.biz.member.dto.TokenResponseDto;
import kr.itycoon.plutoid.biz.member.service.MemberService;
import kr.itycoon.plutoid.global.common.CommonData;
import kr.itycoon.plutoid.global.common.CommonDataHolder;
import kr.itycoon.plutoid.global.common.CommonResult;
import kr.itycoon.plutoid.global.config.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @NonNull
    private final MemberService memberService;

    @NonNull
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<CommonResult<TokenResponseDto>> login(HttpServletResponse response) {

        CommonData commonData = CommonDataHolder.getCommonData();

        log.debug("common data : {} ", commonData);

        if (commonData == null) {
            throw new AuthenticationCredentialsNotFoundException("토큰이 유효하지 않습니다.");
        }

        String accessToken = jwtService.generateAccessToken(commonData.getMemberId(), commonData.getEmail(), commonData.getMemberRole());
        String refreshToken = jwtService.generateRefreshToken(commonData.getMemberId());

        Member member = memberService.findMemberByKey(commonData.getMemberId());

        TokenResponseDto result = new TokenResponseDto(
                member.getMemberId(),
                member.getMemberName(),
                member.getPhotoUrl(),
                accessToken);

        setCookie(response, "_rtkn", refreshToken);
        return ResponseEntity.ok(new CommonResult<TokenResponseDto>(result));
    }

    private void setCookie(HttpServletResponse response, String key, String value) {
        setCookie(response, key, value, "Strict", true, true, null);
    }

    private void setCookie(HttpServletResponse response, String key, String value, String sameSite, boolean isSecure, boolean isHttpOnly, Long maxAge) {

        ResponseCookieBuilder cookieBuilder = ResponseCookie.from(key, value)
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


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> payload) {
        log.debug("payload : {} ", payload);

        return ResponseEntity.ok(new CommonResult<>());
    }

    @PostMapping("/token")
    public ResponseEntity<CommonResult<TokenResponseDto>> refreshAccessToken(HttpServletResponse response, @CookieValue(value = "_rtkn") String refreshToken) {

        log.debug("refreshToken : {}", refreshToken);
        if (refreshToken == null || refreshToken.trim()
                                                .isEmpty()) {
            throw new IllegalArgumentException("권한없는 회원의 요청입니다.");
        }

        // 토큰에서 pk로 회원정보조회
        Map<String, String> tokenData = jwtService.parseToken(refreshToken);
        String memberId = tokenData.get(JwtService.ClaimConstant.KEY_MEMBER_ID);
        Member member = memberService.findMemberByKey(memberId);

        // 새로운 액세스 토큰 발급
        String newAccessToken = jwtService.generateAccessToken(member.getMemberId(), member.getEmail(), member.getMemberRole());
        TokenResponseDto result = new TokenResponseDto(
                member.getMemberId(),
                member.getMemberName(),
                member.getPhotoUrl(),
                newAccessToken);

        log.debug("새로운 엑세스 토큰 발급 ->  TokenResponseDto - result : {} ", result);

        return ResponseEntity.ok(new CommonResult<>(result));
    }

    @PostMapping("/social/callback")
    public ResponseEntity<?> handleSocialLogin(HttpServletResponse response) {
//        URI main = URI.create("http://localhost:5173");
        URI main = URI.create("http://localhost:3000");
        return ResponseEntity.status(303)
                             .location(main)
                             .build();
    }


}
