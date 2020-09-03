package com.apiservice.entity.master.operator;

import org.springframework.security.core.GrantedAuthority;

public enum OperatorRole implements GrantedAuthority {
  SUPER_ADMIN,
  ADMIN,
  SALES_OPERATOR,
  ACCOUNTS_OPERATOR;

  @Override
  public String getAuthority() {
    return this.name();
  }
}
