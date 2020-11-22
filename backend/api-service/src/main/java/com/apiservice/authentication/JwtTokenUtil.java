package com.apiservice.authentication;

import com.apiservice.entity.master.password.PasswordResetConfirmationRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

  @Value("${jwt.token.validity}")
  public long tokenValidity;

  @Value("${jwt.secret}")
  private String secret;

  public UUID getIdentityFromToken(String token) {
    return getClaimFromToken(token, claims -> UUID.fromString(claims.get("identity").toString()));
  }

  public List<Scopes> getScopesFromToken(String token) {
    return (List<Scopes>) getClaimFromToken(token, claims -> claims.get("scopes"));
  }

  public UUID getSecretFromToken(String token) {
    return getClaimFromToken(token, claims -> UUID.fromString(claims.get("secret").toString()));
  }

  private Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
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

  public String generateToken(UUID identity, List<GrantedAuthority> authorities) {
    final Map<String, Object> claims = new HashMap<>();
    claims.putIfAbsent("identity", identity);
    claims.putIfAbsent("scopes", authorities);
    return doGenerateToken(claims);
  }

  public String generateToken(UUID identity) {
    final Map<String, Object> claims = new HashMap<>();
    claims.putIfAbsent("identity", identity);
    return doGenerateToken(claims);
  }

  public String generatePasswordResetConfirmationToken(
      PasswordResetConfirmationRequest confirmationToken) {
    final Map<String, Object> claims = new HashMap<>();
    claims.putIfAbsent("secret", confirmationToken.getToken());
    return doGenerateToken(claims);
  }

  private String doGenerateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public boolean validateTokenIdentity(String token, UUID uuid) {
    return getIdentityFromToken(token).equals(uuid) && !isTokenExpired(token);
  }
}

