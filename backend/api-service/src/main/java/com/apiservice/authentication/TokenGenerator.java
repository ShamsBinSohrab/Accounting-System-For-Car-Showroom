package com.apiservice.authentication;

import com.apiservice.entity.operator.Operator;
import com.apiservice.model.jwt.AuthResponse;
import com.apiservice.service.operator.OperatorService;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
    return AuthResponse.prepare(operator, jwtTokenUtil::generateToken);
  }
}
