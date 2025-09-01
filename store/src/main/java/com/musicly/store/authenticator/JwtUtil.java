package com.musicly.store.authenticator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import com.musicly.store.Services.JwtBlacklistService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class JwtUtil {

  
    private final SecretKey secretKey;
    private int jwtExpiration;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    public JwtUtil(){
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.jwtExpiration = 86400000;
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public String generateTokenWithRole(String userName, String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims,userName);
    }

    private String createToken(Map<String,Object> claims, String Subject) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+ jwtExpiration);

        return Jwts.builder()
        .setClaims(claims)
        .setSubject(Subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(expiryDate)
        .signWith(secretKey)
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
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUserName(String token) {
        return extractClaim(token, null);
    }

    public boolean isTokenBlacklisted(String token, UserDetails userDetails) {
        if(jwtBlacklistService.isTokenBlacklisted(token)){
            return false;
        }
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

}
