package com.apiservice.entity.operator;

import org.springframework.security.core.GrantedAuthority;

public enum OperatorRole implements GrantedAuthority {
  ADMIN,
  SALES_OPERATOR,
  ACCOUNTS_OPERATOR;

  @Override
  public String getAuthority() {
    return this.name();
  }
}