package com.cg_vibely_social_service.configuration.security;

import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${app.jwtSecret}")
    private String jwtKey;

    @Value("${app.jwtExpirationInMs}")
    private Integer tokenLife;

    @Value("${app.jwtRefreshTokenLife}")
    private Integer refreshTokenLife;

    public String generateToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenLife))
                .signWith(getSecretKey(jwtKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenLife))
                .signWith(getSecretKey(jwtKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey(jwtKey)).build().parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractEmail(String bearerToken) {
        String token = extractJwtFromBearToken(bearerToken);
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String bearerToken) {
        String token = extractJwtFromBearToken(bearerToken);
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractJwtFromBearToken(String bearerToken) {
        return bearerToken.substring(7);
    }


    public boolean isTokenValid(String bearerToken) {
        if (bearerToken.startsWith("Bearer ")) {
            Date expiration = extractExpiration(bearerToken);
            return !expiration.before(new Date());
        }
        return false;
    }

    private SecretKey getSecretKey(String jwtKey) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "hmacsha256");
    }
}
