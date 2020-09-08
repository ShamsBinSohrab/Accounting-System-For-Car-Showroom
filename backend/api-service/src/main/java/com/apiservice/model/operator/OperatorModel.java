package com.apiservice.model.operator;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.controller.operator.OperatorController;
import com.apiservice.entity.master.operator.Operator;
import com.apiservice.entity.master.operator.OperatorRole;
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

  public static OperatorModel toModel(Operator operator) {
    return mapper.map(operator, OperatorModel.class).addLinks();
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

  private OperatorModel addLinks() {
    add(linkTo(methodOn(OperatorController.class).details(id)).withSelfRel());
    if (isLoggedInOperator()) {
      add(
          linkTo(methodOn(OperatorController.class).changePassword(null, id))
              .withRel("changePassword"));
    } else if (hasResetPasswordAuthority()) {
      add(
          linkTo(methodOn(OperatorController.class).resetPassword(null, id))
              .withRel("resetPassword"));
    }
    return this;
  }

  private boolean isLoggedInOperator() {
    return SecurityContextHolder.getContext().getAuthentication().getName().equals(username);
  }

  private boolean hasResetPasswordAuthority() {
    return SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .contains(OperatorRole.SUPER_ADMIN)
        || SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .contains(OperatorRole.ADMIN);
  }
}
