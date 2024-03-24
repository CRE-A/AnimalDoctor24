package kr.itycoon.plutoid.global.config.security;

import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import kr.itycoon.plutoid.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

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
            UserDetails userDetails = new CustomUserDetails(
                parsed.get(JwtService.ClaimConstant.KEY_MEMBER_ID),
                parsed.get(JwtService.ClaimConstant.KEY_EMAIL),
                MemberRoleEnum.valueOf(parsed.get(JwtService.ClaimConstant.KEY_MEMBER_ROLE)));

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