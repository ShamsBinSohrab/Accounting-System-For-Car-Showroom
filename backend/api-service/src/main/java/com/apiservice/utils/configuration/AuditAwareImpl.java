package com.apiservice.utils.configuration;

import com.apiservice.authentication.OperatorDetails;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditAwareImpl implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    final Authentication auth;
    return (auth = SecurityContextHolder.getContext().getAuthentication()) == null
        ? Optional.empty()
        : Optional.of(((OperatorDetails) auth.getPrincipal()).getId());
  }
}
