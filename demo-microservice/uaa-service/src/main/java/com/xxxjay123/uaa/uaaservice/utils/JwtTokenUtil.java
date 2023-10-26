package com.xxxjay123.uaa.uaaservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtTokenUtil {

  private final Key secretKey;
  private final long expiration;
  private final Set<String> tokenBlacklist = new HashSet<>();

  public JwtTokenUtil(@Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") long expiration) {
    this.secretKey = new SecretKeySpec(secret.getBytes(),
        SignatureAlgorithm.HS256.getJcaName());
    this.expiration = expiration;
  }


  public String generateToken(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername());
  }

  public String doGenerateToken(Map<String, Object> claims, String username) {
    Date createdDate = new Date();
    Date expirationDate = calculateExpirationDate(createdDate);

    return Jwts.builder()//
        .setClaims(claims)//
        .setSubject(username)//
        .setIssuedAt(createdDate)//

        .setExpiration(expirationDate)//
        .signWith(secretKey, SignatureAlgorithm.HS256) // Sign the token with the injected secretKey
        .compact();//
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    if (isTokenBlacklisted(token)) {
      return false;
    }

    return (username.equals(userDetails.getUsername())
        && !isTokenExpired(token));
  }

  public boolean validateTokens(String token) {
    try {
      final Claims claims = extractClaims(token, secretKey);
      Date now = new Date();
      return now.before(claims.getExpiration());
    } catch (Exception e) {
      return false;
    }
  }


  public String getUsernameFromToken(String token) {
    Claims claims = extractClaims(token, secretKey);
    return claims.getSubject();
  }

  public boolean isTokenExpired(String token) {
    Claims claims = extractClaims(token, secretKey);
    Date now = new Date();
    return now.after(claims.getExpiration());
  }

  private static Claims extractClaims(String token, Key secretKey) {
    return Jwts.parserBuilder()//
        .setSigningKey(secretKey) // Use the injected secretKey
        .build()//
        .parseClaimsJws(token)//
        .getBody();//
  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration);
  }

  public boolean isTokenBlacklisted(String token) {
    return tokenBlacklist.contains(token);
  }

  public void addToBlacklist(String token) {
    tokenBlacklist.add(token);
  }

}
