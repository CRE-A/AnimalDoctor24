package com.jnb.animaldoctor24.global.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnb.animaldoctor24.domain.member.dto.AuthenticationRequest;
import com.jnb.animaldoctor24.global.error.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher("/api/*/auth/login", "POST"));
        setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            AuthenticationRequest loginRequest = objectMapper.readValue(request.getInputStream(), AuthenticationRequest.class);
            System.out.println(loginRequest.getUsername()+" ///"+ loginRequest.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            System.out.println(authenticationToken+"//"+authenticationToken.getPrincipal()+"//"+authenticationToken.getDetails());
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new PreAuthenticatedCredentialsNotFoundException("입력값이 유효하지 않습니다.", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.debug("authentication request successfully ended");
        SecurityContextHolder.getContext()
                .setAuthentication(authResult);
        chain.doFilter(request, response);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("authentication request failed : {}", failed.toString());
        SecurityContextHolder.clearContext();

        HttpStatus code = null;
        if (failed instanceof AuthenticationServiceException) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            code = HttpStatus.UNAUTHORIZED;
        }

        ErrorResponse error = new ErrorResponse(code.name(), code.getReasonPhrase());

        response.setStatus(code.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter()
                .write(objectMapper.writeValueAsString(error));
    }
}
