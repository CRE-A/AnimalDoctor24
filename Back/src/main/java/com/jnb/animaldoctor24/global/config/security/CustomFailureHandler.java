package com.jnb.animaldoctor24.global.config.security;

import com.jnb.animaldoctor24.global.constants.ResponseConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception instanceof BadCredentialsException) {
            errorMessage = ResponseConstants.ID_OR_PASSWORD_ARE_NOT_CORRECT;
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = ResponseConstants.INTERAL_AUTHENTICATION_ERR_OCCURS;
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = ResponseConstants.USER_DOES_NOT_EXISTS2;
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = ResponseConstants.AUTH_CREDENTIALS_DOES_NOT_FOUND;
        } else {
            errorMessage = ResponseConstants.SOMETHING_WENT_WRONG;
        }
        System.out.println("CustomFailureHandler 지나감");
        setDefaultFailureUrl("/api/v1/auth/login?error=true&exception="+errorMessage);
//        setDefaultFailureUrl("/?error=true&exception="+errorMessage);

        super.onAuthenticationFailure(request, response, exception);}

}
