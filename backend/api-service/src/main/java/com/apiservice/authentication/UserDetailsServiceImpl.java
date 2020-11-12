package com.apiservice.authentication;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.service.operator.OperatorService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
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
    final List<GrantedAuthority> authorities =
        Stream.of(operator.getScopes())
            .map(Scopes::valueOf)
            .collect(Collectors.toUnmodifiableList());
    return new User(operator.getUsername(), operator.getPassword(), authorities);
  }
}
