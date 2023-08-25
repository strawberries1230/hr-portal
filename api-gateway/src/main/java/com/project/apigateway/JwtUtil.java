package com.project.apigateway;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    public List<String> extractRolesFromJwt(String token) {
        return extractClaims(token).get("roles", List.class);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }
    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }


}
