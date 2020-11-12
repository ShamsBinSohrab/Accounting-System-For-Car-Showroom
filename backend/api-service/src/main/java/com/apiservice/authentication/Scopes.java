package com.apiservice.authentication;

import java.util.Arrays;
import org.springframework.security.core.GrantedAuthority;

public enum Scopes implements GrantedAuthority {
  COMPANY_READ("company:read"),
  COMPANY_WRITE("company:write"),
  CAR_READ("car:read"),
  CAR_WRITE("car:write"),
  PURCHASE_RECORD_READ("purchase:read"),
  PURCHASE_RECORD_WRITE("purchase:write"),
  SELL_RECORD_READ("sell:read"),
  SELL_RECORD_WRITE("sell:write"),
  EXPENSE_RECORD_READ("expense:read"),
  EXPENSE_RECORD_WRITE("expense:write"),
  OPERATOR_READ("operator:read"),
  OPERATOR_WRITE("operator:write");

  private final String authority;

  Scopes(String authority) {
    this.authority = authority;
  }

  public static Scopes byAuthority(String authority) {
    return Arrays.stream(Scopes.values())
        .filter(s -> s.authority.equals(authority))
        .findFirst().orElseThrow();
  }

  @Override
  public String getAuthority() {
    return authority;
  }
}
