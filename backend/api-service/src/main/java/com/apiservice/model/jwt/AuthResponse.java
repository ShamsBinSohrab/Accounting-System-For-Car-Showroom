package com.apiservice.model.jwt;

import com.apiservice.entity.master.company.Company;
import com.apiservice.entity.master.operator.Operator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import lombok.Getter;

@Getter
public class AuthResponse {

  private String authenticationToken;
  private String tenantAccessorToken;

  public static AuthResponse prepare(Operator operator, Function<String, String> tokenGenerator) {
    AuthResponse response = new AuthResponse();
    response.authenticationToken = tokenGenerator.apply(operator.getUsername());
    response.tenantAccessorToken = Optional.ofNullable(operator.getCompany())
        .map(Company::getUuid)
        .map(Objects::toString)
        .map(tokenGenerator)
        .orElse("");
    return response;
  }
}
