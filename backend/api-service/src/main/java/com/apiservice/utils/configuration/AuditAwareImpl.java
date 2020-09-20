package com.apiservice.utils.configuration;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.repository.operator.OperatorRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<Operator> {

  private final OperatorRepository operatorRepository;

  @Override
  public Optional<Operator> getCurrentAuditor() {
    return operatorRepository
        .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
  }
}
