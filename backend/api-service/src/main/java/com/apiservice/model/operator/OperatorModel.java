package com.apiservice.model.operator;

import com.apiservice.entity.operator.Operator;
import com.apiservice.entity.operator.OperatorRole;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class OperatorModel {

  private static final ModelMapper mapper = new ModelMapper();

  private long id;
  private String username;
  private String password;
  private OperatorRole role;

  public static OperatorModel toModel(Operator operator) {
    return mapper.map(operator, OperatorModel.class);
  }

  public Operator toOperator() {
    return mapper.map(this, Operator.class);
  }

  public void updateEntity(Operator operator) {
    final long id = operator.getId();
    final String password = operator.getPassword();
    mapper.map(this, operator);
    operator.setId(id);
    operator.setPassword(password);
  }

  public Operator toEntity() {
    return mapper.map(this, Operator.class);
  }
}
