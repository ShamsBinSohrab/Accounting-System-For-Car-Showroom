package com.apiservice.model.operator;

import com.apiservice.entity.car.Car;
import com.apiservice.entity.operator.Operator;
import com.apiservice.entity.operator.OperatorRole;
import com.apiservice.model.car.CarModel;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class OperatorModel {

  private static final ModelMapper mapper = new ModelMapper();

  static {
    mapper.createTypeMap(OperatorModel.class, Operator.class, "UpdateEntity")
            .addMappings(mapper -> mapper.skip(Operator::setId));
  }
  private String username;
  private String password;
  private OperatorRole role;

  public Operator toUsers(PasswordEncoder passwordEncoder) {
    Operator operator = mapper.map(this, Operator.class);
    operator.setPassword(passwordEncoder.encode(this.getPassword()));
    return operator;
  }
  public static OperatorModel toModel(Operator operator) {
    return mapper.map(operator, OperatorModel.class);
  }
  public void updateEntity(Operator operator) {
    mapper.map(this, operator, "UpdateEntity");
  }
  public Operator toEntity() {
    return mapper.map(this, Operator.class);
  }
}
