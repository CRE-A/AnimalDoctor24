package com.jnb.animaldoctor24.domain.member.application;

import com.jnb.animaldoctor24.domain.hospital.application.impl.HospitalServiceImpl;
import com.jnb.animaldoctor24.domain.member.dto.MemberModiyRequest;
import com.jnb.animaldoctor24.global.config.jwt.JwtService;
import com.jnb.animaldoctor24.domain.member.domain.Member;
import com.jnb.animaldoctor24.domain.member.domain.Role;
import com.jnb.animaldoctor24.global.error.exception.DataAlreadyExistException;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import com.jnb.animaldoctor24.global.util.Utils;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationRequest;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationResponse;
import com.jnb.animaldoctor24.domain.member.dto.MemberRegisterRequest;
import com.jnb.animaldoctor24.domain.member.dao.MemberRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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
public class AuthenticationServiceImpl implements AuthenticationService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

    private final JwtService jwtService;
    private final MemberRepo memberRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager em;

    Role role;



    @Override
    public ResponseEntity<String> register(MemberRegisterRequest request) {
        Optional<Member> user = memberRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new DataAlreadyExistException(ResponseConstants.USER_ALREADY_EXISTS);
        }

        memberRepository.save(getUserFromRequest(request));

        Member validUser = memberRepository.findByEmail(request.getEmail()).get();
        String jwtToken = jwtService.generateToken(validUser);
        return Utils.getResponseEntity(ResponseConstants.USER_SIGNUP_SUCCESS + " " + jwtToken, HttpStatus.OK);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Member user = memberRepository.findByEmail(request.getEmail()).
                orElseThrow(() -> new RuntimeException(ResponseConstants.USER_LOGIN_FAILED));
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
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