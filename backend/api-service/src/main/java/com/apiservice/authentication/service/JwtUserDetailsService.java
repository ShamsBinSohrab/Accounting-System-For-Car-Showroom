package com.apiservice.authentication.service;

import com.apiservice.entity.operator.Operator;
import com.apiservice.service.operator.OperatorService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final OperatorService operatorService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Operator operator = operatorService.getByUsername(username);
    return new User(operator.getUsername(), operator.getPassword(),
        Collections.singletonList(operator.getRole()));
  }

  public void save(Operator operator) {
    operatorService.save(operator);
  }
}
