package com.apiservice.authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {

  private final JwtTokenUtil jwtTokenUtil;

  public UUID extractIdentityFromToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader("Authorization"))
        .map(m -> m.substring(7))
        .map(jwtTokenUtil::getIdentityFromToken)
        .orElseThrow();
  }

  public List<Scopes> extractScopesFromToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader("Authorization"))
        .map(m -> m.substring(7))
        .map(jwtTokenUtil::getScopesFromToken)
        .orElseThrow();
  }

}
