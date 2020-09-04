package com.apiservice.model.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {

  private final String authToken;
  private final String companyToken;

  public static AuthResponse prepare(String authenticationToken, String tenantAccessorToken) {
    return new AuthResponse(authenticationToken, tenantAccessorToken);
  }
}
