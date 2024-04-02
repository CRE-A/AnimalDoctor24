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

        try {
            Optional<User> user = userRepository.findByEmail(request.getEmail());
            if (user.isEmpty()) {
                userRepository.save(getUserFromRequest(request));
                User validUser = userRepository.findByEmail(request.getEmail()).get();
                String jwtToken = jwtService.generateToken(validUser);
                System.out.println(jwtToken);
                return Utils.getResponseEntity(ResponseConstants.USER_SIGNUP_SUCCESS + " " + jwtToken, HttpStatus.OK);
            } else {
                throw new DataAlreadyExistException(ResponseConstants.USER_ALREADY_EXISTS);
            }
        } catch (DataAlreadyExistException e) {
            throw new DataAlreadyExistException(e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Utils.getResponseEntity(ResponseConstants.SOME_THING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        System.out.println("authnticate 여기 지나감");
        User user = userRepository.findByEmail(request.getEmail()).
                orElseThrow(() -> new RuntimeException(ResponseConstants.USER_LOGIN_FAILED));
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();

    }


    private boolean validateSignupRequest(RegisterRequest request) {
        return request.getFirstname() != null && request.getLastname() != null && request.getEmail() != null && request.getPassword() != null;
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




//    public ResponseEntity<String> register(RegisterRequest request) {
//        // first thing check if the request is valid or not !!
//        try {
//            if (validateSignupRequest(request)) {
//                // if the request is valid then lets check if the user already exists or not
//                Optional<User> user = userRepository.findByEmail(request.getEmail());
//                if (user.isEmpty()) {
//                    userRepository.save(getUserFromRequest(request));
//                    var validUser = userRepository.findByEmail(request.getEmail()).get();
//                    var jwtToken = jwtService.generateToken(validUser);
//                    System.out.println(jwtToken);
//                    return Utils.getResponseEntity(ResponseConstants.USER_SIGNUP_SUCCESS + " " + jwtToken, HttpStatus.OK);
//                } else {
//                    return Utils.getResponseEntity(ResponseConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
//                }
//            } else {
//                return Utils.getResponseEntity(ResponseConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return Utils.getResponseEntity(ResponseConstants.SOME_THING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}