package com.apiservice.model.jwt;

import com.apiservice.entity.company.Company;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class JwtResponse {

  private String authenticationToken;
  private String tenantAccessorToken;

  public static JwtResponse prepare(
      UserDetails userDetails,
      Company company,
      Function<UserDetails, String> authTokenGenerator,
      Function<Company, String> tenantTokenGenerator) {
    JwtResponse response = new JwtResponse();
    response.authenticationToken = authTokenGenerator.apply(userDetails);
    response.tenantAccessorToken = tenantTokenGenerator.apply(company);
    return response;
  }
}
