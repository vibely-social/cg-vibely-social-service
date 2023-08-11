package com.vibely_social.configuration.security;

import com.vibely_social.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${app.jwtSecret}")
    private  String jwtKey;

    @Value("${app.jwtExpirationInMs}")
    private Integer expiration;

    public String generateToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(getSecretKey(jwtKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey(jwtKey)).build().parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token) {
        Date expiration = extractExpiration(token);
        return !expiration.before(new Date());
    }

    private SecretKey getSecretKey(String jwtKey) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "hmacsha256");
    }
}
