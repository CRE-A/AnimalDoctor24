package com.jnb.animaldoctor24.global.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.jnb.animaldoctor24.domain.member.domain.Member;
import com.jnb.animaldoctor24.domain.member.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.accessToken.expireSec}")
    private long jwtAccessTokenExpireSec;
    @Value("${jwt.refreshToken.expireSec}")
    private long jwtRefreshTokenExpireSec;


    public String generateAccessToken(String email, Role memberRole) {

        final Date issueDate = new Date(System.currentTimeMillis());
        final Date expireDate = new Date(issueDate.getTime() + (jwtAccessTokenExpireSec * 1000L));

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(issueDate)
                .withExpiresAt(expireDate)
                .withClaim(ClaimConstant.KEY_EMAIL, email)
                .withClaim(ClaimConstant.KEY_MEMBER_ROLE, memberRole.name())
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

//    public String generateAccessToken(String member) {
//
//        final Date issueDate = new Date(System.currentTimeMillis());
//        final Date expireDate = new Date(issueDate.getTime() + (jwtAccessTokenExpireSec * 1000L));
//
//        return JWT.create()
//                .withSubject(member.getEmail())
//                .withIssuedAt(issueDate)
//                .withExpiresAt(expireDate)
//                .withClaim(ClaimConstant.KEY_EMAIL, member.getEmail())
//                .withClaim(ClaimConstant.KEY_MEMBER_ROLE, member.getRole().toString())
//                .sign(Algorithm.HMAC512(SECRET_KEY));
//    }

    public String generateRefreshToken(String email) {

        final Date issueDate = new Date(System.currentTimeMillis());
        final Date expireDate = new Date(issueDate.getTime() + (jwtRefreshTokenExpireSec * 1000L));

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(issueDate)
                .withExpiresAt(expireDate)
                .withClaim(ClaimConstant.KEY_EMAIL, email)
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }


    public Map<String, String> parseToken(String token) {
        try {
            final Date date = new Date();
            DecodedJWT decoded = JWT.decode(token);

            if (date.compareTo(decoded.getExpiresAt()) > 0) {
                throw new NonceExpiredException("토큰 유효기간이 만료되었습니다.");
            }

            Verification verification = JWT.require(Algorithm.HMAC512(SECRET_KEY));
            verification.build().verify(token);

            Map<String, String> map = new HashMap<>();
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
//    public String generateAccessToken(UserDetails userDetails){
//        return generateAccessToken( new HashMap<>(),userDetails);
//    }
//    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//
//        final Date issueDate = new Date(System.currentTimeMillis());
//        final Date expireDate = new Date(issueDate.getTime() + (jwtAccessTokenExpireSec * 1000L));
//
//        return Jwts.builder().setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
////                .setIssuedAt(new Date(System.currentTimeMillis()))
////                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                .setIssuedAt(issueDate)
//                .setExpiration(expireDate)
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//
//    }

//    public String generateRefreshToken(UserDetails userDetails){
//        return generateRefreshToken( new HashMap<>(), userDetails);
//    }
//
//    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//
//        final Date issueDate = new Date(System.currentTimeMillis());
//        final Date expireDate = new Date(issueDate.getTime() + (jwtRefreshTokenExpireSec * 1000L));
//
//        return Jwts.builder().setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
////                .setIssuedAt(new Date(System.currentTimeMillis()))
////                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                .setIssuedAt(issueDate)
//                .setExpiration(expireDate)
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (userDetails.getUsername().equals(username) && !isTokenExpired(token));
//    }
//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }


    public static final class ClaimConstant {

        public static final String KEY_EMAIL = "email";
        public static final String KEY_MEMBER_ROLE = "memberRole";

        private ClaimConstant() {}
    }
}
