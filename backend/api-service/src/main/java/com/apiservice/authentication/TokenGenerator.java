package com.apiservice.authentication;

import com.apiservice.entity.master.company.Company;
import com.apiservice.entity.master.operator.Operator;
import com.apiservice.model.auth.AuthResponse;
import com.apiservice.service.operator.OperatorService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TokenGenerator implements Callable<AuthResponse> {

  private final JwtTokenUtil jwtTokenUtil;
  private final OperatorService operatorService;

  @Setter
  private String username;

  @Override
  public AuthResponse call() throws UsernameNotFoundException {
    final Operator operator = operatorService.getByUsername(username);
    final String authToken =
        jwtTokenUtil.generateToken(operator.getUuid(), getAuthorities(operator));
    final String companyToken = Optional.ofNullable(operator.getCompany())
        .map(Company::getUuid)
        .map(jwtTokenUtil::generateToken)
        .orElse("");
    return AuthResponse.prepare(authToken, companyToken);
  }

  private List<GrantedAuthority> getAuthorities(Operator operator) {
    return Stream.of(operator.getScopes())
        .map(Scopes::valueOf)
        .collect(Collectors.toUnmodifiableList());
  }
}
