package com.apiservice.multitenancy;

import com.apiservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

  private final Constants constants;

  @Override
  public String resolveCurrentTenantIdentifier() {
    return TenantContext.isTenantSet() ? TenantContext.getCurrentTenant()
        : constants.defaultCompanyIdentifier;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
