package com.musicly.store.authenticator;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String secretKey ="myScecretKey";
    private int jwtExpiration = 86400000;

    public String GenerateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return CreateToken(claims, userDetails.getUsername());
    }

    public String generateTokenWithRole(String userName, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return CreateToken(claims,userName);
    }

    private String CreateToken(Map<String,Object> claims, String Subject) {
        return Jwts.builder()
        .setClaims(claims)
        .setSubject(Subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    String extractUserName(String token) {
        return extractClaim(token, null);
    }

}
