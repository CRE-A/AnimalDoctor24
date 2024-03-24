package kr.itycoon.plutoid.global.aop;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import kr.itycoon.plutoid.global.common.CommonData;
import kr.itycoon.plutoid.global.common.CommonDataHolder;
import kr.itycoon.plutoid.global.config.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * 요청정보 인터셉터
 *
 * @author likejy
 */
@Component
@Slf4j
public class CommonRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        }

        // 공통 데이터 생성
        CommonData commonData = null;
        if (userDetails == null) {
            commonData = new CommonData(
                    "system",
                    null,
                    null,
                    parseIpAddress(request),
                    request.getRequestURI(),
                    request.getMethod());
        } else {
            commonData = new CommonData(
                    userDetails.getMemberId(),
                    userDetails.getEmail(),
                    userDetails.getMemberRole(),
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