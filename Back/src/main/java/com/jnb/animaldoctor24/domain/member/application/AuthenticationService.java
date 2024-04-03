package com.jnb.animaldoctor24.domain.member.application;

import com.jnb.animaldoctor24.global.config.jwt.JwtService;
import com.jnb.animaldoctor24.domain.member.domain.User;
import com.jnb.animaldoctor24.domain.member.domain.Role;
import com.jnb.animaldoctor24.global.error.exception.DataAlreadyExistException;
import com.jnb.animaldoctor24.global.error.exception.DataNotFoundException;
import com.jnb.animaldoctor24.global.util.Utils;
import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationRequest;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationResponse;
import com.jnb.animaldoctor24.domain.member.dto.RegisterRequest;
import com.jnb.animaldoctor24.domain.member.dao.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepo userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    Role role;



    public ResponseEntity<String> register(RegisterRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new DataAlreadyExistException(ResponseConstants.USER_ALREADY_EXISTS);
        }

        userRepository.save(getUserFromRequest(request));

        User validUser = userRepository.findByEmail(request.getEmail()).get();
        String jwtToken = jwtService.generateToken(validUser);
        return Utils.getResponseEntity(ResponseConstants.USER_SIGNUP_SUCCESS + " " + jwtToken, HttpStatus.OK);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).
                orElseThrow(() -> new RuntimeException(ResponseConstants.USER_LOGIN_FAILED));
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse reAuthenticate(AuthenticationRequest request, String email) {
        return AuthenticationResponse.builder()
                .build();
    }

    public void withdraw(String email) {
    }

    private User getUserFromRequest(RegisterRequest request) {
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        return user;
    }



}