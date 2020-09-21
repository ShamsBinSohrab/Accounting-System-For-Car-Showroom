package com.apiservice.authentication;

import com.apiservice.entity.master.operator.Operator;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class OperatorDetails extends User {

  private final long id;

  private OperatorDetails(
      long id,
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.id = id;
  }

  public static OperatorDetails of(Operator operator) {
    return new OperatorDetails(
        operator.getId(),
        operator.getUsername(),
        operator.getPassword(),
        List.of(operator.getRole()));
  }
}
