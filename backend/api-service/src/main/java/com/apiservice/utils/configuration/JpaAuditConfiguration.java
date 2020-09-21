package com.apiservice.utils.configuration;

import com.apiservice.authentication.OperatorDetails;
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
@EnableJpaAuditing(auditorAwareRef = "auditor", modifyOnCreate = false)
public class JpaAuditConfiguration {

  @Bean
  public AuditorAware<Long> auditor() {
    return () ->
        SecurityContextHolder.getContext().getAuthentication() == null
            ? Optional.empty()
            : Optional.of(
                ((OperatorDetails)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getId());
  }
}
