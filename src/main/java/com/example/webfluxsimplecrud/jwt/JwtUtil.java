package com.example.webfluxsimplecrud.jwt;

import com.example.webfluxsimplecrud.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtil {
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    private static final String BEARER_PREFIX = "Bearer ";

    private static final Date ACCESS_TIME = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
    public static final Date REFRESH_TIME = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.secret.key}")
    private String SECURITY_KEY;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(SECURITY_KEY);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public TokenDto createAllToken(String userId) {
        return new TokenDto(createToken(userId, ACCESS_TOKEN),
                createToken(userId, REFRESH_TOKEN));
    }

    public String createToken(String userId, String type) {
        Date date = new Date();
        Date exprTime = type.equals(JwtUtil.ACCESS_TOKEN) ? ACCESS_TIME : REFRESH_TIME;

        return BEARER_PREFIX +
                Jwts.builder()
                        .signWith(key, signatureAlgorithm)
                        .setSubject(userId)
                        .setExpiration(exprTime)
                        .setIssuedAt(date)
                        .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECURITY_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("인증 실패");
        }
    }

    public String getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}
