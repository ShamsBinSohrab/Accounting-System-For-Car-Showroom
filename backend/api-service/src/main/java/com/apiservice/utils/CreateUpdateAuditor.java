package com.apiservice.utils;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.entity.tenant.Auditable;
import com.apiservice.repository.operator.OperatorRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUpdateAuditor {

  private final OperatorRepository operatorRepository;

  public void auditCreateUpdate(Auditable auditable) {
    Operator operator =
        operatorRepository
            .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
            .orElseThrow();
    if (auditable.isCreating()) {
      auditable.setCreatedBy(operator);
      auditable.setCreatedDate(LocalDateTime.now());
    } else {
      auditable.setLastModifiedBy(operator);
      auditable.setLastModifiedDate(LocalDateTime.now());
    }
  }
}
