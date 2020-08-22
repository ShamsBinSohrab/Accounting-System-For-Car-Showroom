package com.apiservice.model.operator;

import com.apiservice.entity.operator.Operator;
import com.apiservice.entity.operator.OperatorRole;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class OperatorModel {

  private static final ModelMapper mapper = new ModelMapper();

  private String username;
  private String password;
  private OperatorRole role;

  public Operator toUsers(PasswordEncoder passwordEncoder) {
    Operator operator = mapper.map(this, Operator.class);
    operator.setPassword(passwordEncoder.encode(this.getPassword()));
    return operator;
  }
}
