package com.apiservice.authentication;

import org.springframework.security.core.GrantedAuthority;

public enum Scopes implements GrantedAuthority {
  COMPANY_READ,
  COMPANY_WRITE,
  CAR_READ,
  CAR_WRITE,
  PURCHASE_RECORD_READ,
  PURCHASE_RECORD_WRITE,
  SELL_RECORD_READ,
  SELL_RECORD_WRITE,
  EXPENSE_RECORD_READ,
  EXPENSE_RECORD_WRITE,
  OPERATOR_READ,
  OPERATOR_WRITE;

  @Override
  public String getAuthority() {
    return name();
  }
}
