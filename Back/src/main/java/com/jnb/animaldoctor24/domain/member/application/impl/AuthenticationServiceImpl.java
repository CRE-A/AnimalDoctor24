package com.jnb.animaldoctor24.domain.member.application.impl;

import com.jnb.animaldoctor24.domain.hospital.application.impl.HospitalServiceImpl;
import com.jnb.animaldoctor24.domain.member.application.AuthenticationService;
import com.jnb.animaldoctor24.domain.member.dto.*;
import com.jnb.animaldoctor24.global.common.CommonData;
import com.jnb.animaldoctor24.global.common.CommonDataHolder;
import com.jnb.animaldoctor24.global.common.CommonResult;
import com.jnb.animaldoctor24.global.config.security.jwt.JwtService;
import com.jnb.animaldoctor24.domain.member.domain.Member;
import com.jnb.animaldoctor24.domain.member.domain.Role;
import com.jnb.animaldoctor24.global.error.exception.DataAlreadyExistException;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import com.jnb.animaldoctor24.global.util.Utils;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.domain.member.dao.MemberRepo;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

    private final JwtService jwtService;
    private final MemberRepo memberRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager em;

    Role role;


    @PostMapping("/login")
    public ResponseEntity<CommonResult<TokenResponse>> login(HttpServletResponse response) {

        CommonData commonData = CommonDataHolder.getCommonData();

        log.debug("common data : {} ", commonData);

        if (commonData == null) {
            throw new AuthenticationCredentialsNotFoundException("토큰이 유효하지 않습니다.");
        }

        String accessToken = jwtService.generateAccessToken(commonData.getEmail(), commonData.getRole());
        String refreshToken = jwtService.generateRefreshToken(commonData.getEmail());

        Member member = memberRepository.findByEmail(commonData.getEmail()).get();

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
        String memberId = tokenData.get(JwtService.ClaimConstant.KEY_EMAIL);
        Member member = memberRepository.findByEmail(memberId).get();

        // 새로운 액세스 토큰 발급
        String newAccessToken = jwtService.generateAccessToken(member.getEmail(), member.getRole());
        TokenResponse result = new TokenResponse(
                member.getEmail(),
                member.getFirstName(),
                member.getImagePath(),
                newAccessToken);

        log.debug("새로운 엑세스 토큰 발급 ->  TokenResponseDto - result : {} ", result);

        return ResponseEntity.ok(new CommonResult<TokenResponse>(result));
    }







    @Override
    public ResponseEntity<String> register(MemberRegisterRequest request) {
        Optional<Member> user = memberRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new DataAlreadyExistException(ResponseConstants.USER_ALREADY_EXISTS);
        }

        memberRepository.save(getUserFromRequest(request));

        Member validUser = memberRepository.findByEmail(request.getEmail()).get();
        String jwtToken = jwtService.generateAccessToken(validUser.getEmail(),validUser.getRole());
        return Utils.getResponseEntity(ResponseConstants.USER_SIGNUP_SUCCESS + " " + jwtToken, HttpStatus.OK);
    }





    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Member member = memberRepository.findByEmail(request.getEmail()).
                orElseThrow(() -> new RuntimeException());
        String accessToken = jwtService.generateAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtService.generateRefreshToken(member.getEmail());

        setCookie(response, "_rtkn", refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }


    @Override
    public ResponseEntity<String> reAuthenticate(MemberModiyRequest request, String email) {
        Optional<Member> memberInfo = memberRepository.findByEmail(email);
        if(memberInfo.isEmpty()) {
            throw new DataNotFoundException(ResponseConstants.USER_DOES_NOT_EXISTS2);
        }

        updateMemberInfo(request, email);
        return Utils.getResponseEntity(ResponseConstants.HOSPITAL_MODIFY_SUCCESS, HttpStatus.OK);
    }


    @Override
    public void withdraw(String email) {
        Optional<Member> memberInfo = memberRepository.findByEmail(email);
        if(memberInfo.isEmpty()) {
            throw new DataNotFoundException(ResponseConstants.USER_DOES_NOT_EXISTS);
        }

        memberRepository.delete(memberInfo.get());
    }


    private Member getUserFromRequest(MemberRegisterRequest request) {
        Member member = new Member();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setPhoneNumber(request.getPhoneNumber());
        member.setRole(request.getRole());
        member.setAnimalName(request.getAnimalName());
        member.setAnimalGender(request.getAnimalGender());
        member.setAnimalBreed(request.getAnimalBreed());
        member.setImagePath(request.getImagePath());
        return member;
    }

    private void updateMemberInfo(MemberModiyRequest request, String email) {
        Member member = em.find(Member.class, email);

        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setPhoneNumber(request.getPhoneNumber());
        member.setRole(request.getRole());
        member.setAnimalName(request.getAnimalName());
        member.setAnimalGender(request.getAnimalGender());
        member.setAnimalBreed(request.getAnimalBreed());
        member.setImagePath(request.getImagePath());
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