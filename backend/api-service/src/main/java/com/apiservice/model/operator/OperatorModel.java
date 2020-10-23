package com.apiservice.model.operator;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.controller.operator.OperatorController;
import com.apiservice.entity.master.operator.Operator;
import com.apiservice.entity.master.operator.OperatorRole;
import javax.validation.constraints.Email;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
public class OperatorModel extends RepresentationModel<OperatorModel> {

  private static final ModelMapper mapper = new ModelMapper();

  private long id;
  private String username;
  private String password;
  private OperatorRole role;

  @Email(message = "Invalid email address")
  private String email;

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
}
