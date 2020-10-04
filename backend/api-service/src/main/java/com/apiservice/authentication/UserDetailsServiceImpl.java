package com.apiservice.authentication;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.service.operator.OperatorService;
import java.util.List;
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
    final Operator operator = operatorService.getByUsername(username);
    return new User(operator.getUsername(), operator.getPassword(), List.of(operator.getRole()));
  }
}
