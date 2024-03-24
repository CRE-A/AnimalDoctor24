package kr.itycoon.plutoid.global.config.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import kr.itycoon.plutoid.biz.common.model.MemberRoleEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {
    
    // TODO: 설정파일에서 값 읽어오도록 수정할 것
    @Value("${plutoid.jwt.secret}")
    private String jwtSecretKey;

    // TODO: 설정파일에서 값 읽어오도록 수정할 것
    @Value("${plutoid.jwt.accessToken.expireSec}")
    private long jwtAccessTokenExpireSec;

    // TODO: 설정파일에서 값 읽어오도록 수정할 것
    @Value("${plutoid.jwt.refreshToken.expireSec}")
    private long jwtRefreshTokenExpireSec;

    public String generateAccessToken(String memberId, String email, MemberRoleEnum memberRole) {

        final Date issueDate = new Date(System.currentTimeMillis());
        final Date expireDate = new Date(issueDate.getTime() + (jwtAccessTokenExpireSec * 1000L));

        return JWT.create()
            .withSubject(memberId)
            .withIssuedAt(issueDate)
            .withExpiresAt(expireDate)
            .withClaim(ClaimConstant.KEY_MEMBER_ID, memberId)
            .withClaim(ClaimConstant.KEY_EMAIL, email)
            .withClaim(ClaimConstant.KEY_MEMBER_ROLE, memberRole.name())
            .sign(Algorithm.HMAC512(jwtSecretKey));
    }

    public String generateRefreshToken(String memberId) {

        final Date issueDate = new Date(System.currentTimeMillis());
        final Date expireDate = new Date(issueDate.getTime() + (jwtRefreshTokenExpireSec * 1000L));

        return JWT.create()
            .withSubject(memberId)
            .withIssuedAt(issueDate)
            .withExpiresAt(expireDate)
            .withClaim(ClaimConstant.KEY_MEMBER_ID, memberId)
            .sign(Algorithm.HMAC512(jwtSecretKey));
    }

    public Map<String, String> parseToken(String token) {
        try {
            final Date date = new Date();
            DecodedJWT decoded = JWT.decode(token);

            if (date.compareTo(decoded.getExpiresAt()) > 0) {
                throw new NonceExpiredException("토큰 유효기간이 만료되었습니다.");
            }
            
            Verification verification = JWT.require(Algorithm.HMAC512(jwtSecretKey));
            verification.build().verify(token);
            
            Map<String, String> map = new HashMap<>();
            map.put(ClaimConstant.KEY_MEMBER_ID, decoded.getClaim(ClaimConstant.KEY_MEMBER_ID).asString());
            map.put(ClaimConstant.KEY_EMAIL, decoded.getClaim(ClaimConstant.KEY_EMAIL).asString());
            map.put(ClaimConstant.KEY_MEMBER_ROLE, decoded.getClaim(ClaimConstant.KEY_MEMBER_ROLE).asString());
            log.debug("map : {} " , map);
            return map;
        } catch (AuthenticationException e) {
            throw e;
        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("토큰이 유효하지 않습니다.", e);
        }
    }
    

    public static final class ClaimConstant {

        public static final String KEY_MEMBER_ID = "memberId";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_MEMBER_ROLE = "memberRole";

        private ClaimConstant() {}
    }
}
