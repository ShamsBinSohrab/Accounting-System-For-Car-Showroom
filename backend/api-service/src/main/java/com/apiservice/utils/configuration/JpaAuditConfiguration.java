package com.apiservice.utils.configuration;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.repository.operator.OperatorRepository;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditor", modifyOnCreate = false)
public class JpaAuditConfiguration {

  @Bean
  public AuditorAware<Long> auditor() {
    return new AuditAwareImpl();
  }
}
