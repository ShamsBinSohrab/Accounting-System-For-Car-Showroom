package com.apiservice.authentication;

import com.apiservice.entity.master.company.Company;
import com.apiservice.entity.master.password.PasswordResetConfirmationRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

  @Value("${jwt.token.validity}")
  public long tokenValidity;

  @Value("${jwt.secret}")
  private String secret;

  public String getSubjectFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateAuthToken(UserDetails userDetails) {
    final Map<String, Object> claims = new HashMap<>();
    final String authorities =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    claims.putIfAbsent("AUTHORITIES", authorities);
    return doGenerateToken(claims, userDetails.getUsername());
  }

  public String generateCompanyToken(Company company) {
    final Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, company.getUuid().toString());
  }

  public String generatePasswordResetConfirmationToken(
      PasswordResetConfirmationRequest confirmationToken) {
    final Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, confirmationToken.getToken().toString());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Boolean validateAuthToken(String token, UserDetails userDetails) {
    final String username = getSubjectFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public Boolean validateTenantToken(String token, Company company) {
    final String uuid = getSubjectFromToken(token);
    return (uuid.equals(company.getUuid().toString()) && !isTokenExpired(token));
  }
}

