package com.jnb.animaldoctor24.domain.member.application.impl;

import com.jnb.animaldoctor24.domain.hospital.application.impl.HospitalServiceImpl;
import com.jnb.animaldoctor24.domain.member.application.MemberService;
import com.jnb.animaldoctor24.domain.member.dto.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

    private final JwtService jwtService;
    private final MemberRepo memberRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager em;

    Role role;


    @Override
    public Member findMemberByEmail(String email){
        return memberRepository.findByEmail(email).get();
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Member member = memberRepository.findByEmail(request.getUsername()).
                orElseThrow(() -> new RuntimeException());
        String accessToken = jwtService.generateAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtService.generateRefreshToken(member.getEmail());

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



}