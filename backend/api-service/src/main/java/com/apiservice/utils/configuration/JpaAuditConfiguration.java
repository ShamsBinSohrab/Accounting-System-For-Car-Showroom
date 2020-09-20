package com.apiservice.utils.configuration;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.repository.operator.OperatorRepository;
import com.apiservice.service.operator.OperatorService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class JpaAuditConfiguration {

  private final OperatorRepository operatorRepository;

  @Bean
  public AuditorAware<Operator> auditor() {
    return () ->
        operatorRepository.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName());
  }
}
