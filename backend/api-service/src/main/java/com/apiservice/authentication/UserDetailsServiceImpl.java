package com.apiservice.authentication;

import com.apiservice.entity.company.Company;
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
public class UserDetailsServiceImpl implements UserDetailsService {

  private final OperatorService operatorService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Operator operator = operatorService.getByUsername(username);
    return new User(operator.getUsername(), operator.getPassword(),
        Collections.singletonList(operator.getRole()));
  }
}
