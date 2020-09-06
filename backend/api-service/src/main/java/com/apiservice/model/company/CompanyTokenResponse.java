package com.apiservice.model.company;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CompanyTokenResponse {
  private final String companyToken;

  public static CompanyTokenResponse of(String token) {
    return new CompanyTokenResponse(token);
  }
}
