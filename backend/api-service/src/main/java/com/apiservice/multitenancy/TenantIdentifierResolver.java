package com.apiservice.multitenancy;

import lombok.RequiredArgsConstructor;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {


  @Override
  public String resolveCurrentTenantIdentifier() {
    return TenantContext.isTenantSet() ? TenantContext.getCurrentTenant() : "public";
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
