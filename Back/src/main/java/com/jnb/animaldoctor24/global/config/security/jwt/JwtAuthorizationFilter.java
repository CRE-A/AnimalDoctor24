package com.jnb.animaldoctor24.global.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnb.animaldoctor24.domain.member.domain.Member;
import com.jnb.animaldoctor24.domain.member.domain.Role;
import com.jnb.animaldoctor24.global.error.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final ObjectMapper objectMapper;

    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, JwtService jwtService) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.debug("url: {}", request.getRequestURL());
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Map<String, String> parsed = jwtService.parseToken(jwtToken.replace("Bearer ", ""));
            UserDetails userDetails = new Member(
                    parsed.get(JwtService.ClaimConstant.KEY_EMAIL),
                    Role.valueOf(parsed.get(JwtService.ClaimConstant.KEY_MEMBER_ROLE)));

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            onSuccessfulAuthentication(request, response, authentication);
            chain.doFilter(request, response);
        } catch (AuthenticationException e) {
            onUnsuccessfulAuthentication(request, response, e);
        } catch (Exception e) {
            onUnsuccessfulAuthentication(request, response, new AuthenticationServiceException("", e));
        }
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {

        log.debug("authorization request successfully ended");
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException  {

        log.info("authorization request failed : {}", failed.toString());
        SecurityContextHolder.clearContext();

        HttpStatus code = null;
        if (failed instanceof AuthenticationCredentialsNotFoundException) {
            code = HttpStatus.UNAUTHORIZED;
        } else if (failed instanceof CredentialsExpiredException) {
            code = HttpStatus.UNAUTHORIZED;
        } else if (failed instanceof BadCredentialsException) {
            code = HttpStatus.UNAUTHORIZED;
        } else if (failed instanceof AuthenticationServiceException) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            code = HttpStatus.FORBIDDEN;
        }

        ErrorResponse error = new ErrorResponse(code.name(),code.getReasonPhrase());

        response.setStatus(code.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }

}
