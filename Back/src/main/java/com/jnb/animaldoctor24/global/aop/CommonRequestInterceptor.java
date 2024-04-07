package com.jnb.animaldoctor24.global.aop;

import com.jnb.animaldoctor24.domain.member.domain.Member;
import com.jnb.animaldoctor24.global.common.CommonData;
import com.jnb.animaldoctor24.global.common.CommonDataHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class CommonRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member userDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof Member) {
            userDetails = (Member) authentication.getPrincipal();
        }

        // 공통 데이터 생성
        CommonData commonData = null;
        if (userDetails == null) {
            commonData = new CommonData(
                    "system@dev.com",
                    null,
                    parseIpAddress(request),
                    request.getRequestURI(),
                    request.getMethod());
        } else {
            commonData = new CommonData(
                    userDetails.getEmail(),
                    userDetails.getRole(),
                    parseIpAddress(request),
                    request.getRequestURI(),
                    request.getMethod());
        }
        CommonDataHolder.setCommonData(commonData);

        if (log.isDebugEnabled()) {
            log.debug("url : [{}] {}", request.getMethod(), request.getQueryString() == null ? request.getRequestURI() : request.getRequestURI() + "?" + request.getQueryString());
            log.debug("{}", commonData.toString());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        CommonDataHolder.resetCommonData();
    }

    private String parseIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

}
