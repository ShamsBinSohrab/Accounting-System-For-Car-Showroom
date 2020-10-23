package com.apiservice.model.operator;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.controller.operator.OperatorController;
import com.apiservice.entity.master.operator.Operator;
import com.apiservice.entity.master.operator.OperatorRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperatorModelAssembler implements
    RepresentationModelAssembler<Operator, OperatorModel> {

  private final ModelMapper mapper;

  @Override
  public OperatorModel toModel(Operator operator) {
    final OperatorModel model = mapper.map(operator, OperatorModel.class);
    addLinkToDetails(model);
    if (isAdminOrSuperAdmin()) {
      addLinkToCreate(model);
      addLinkToUpdate(model);
      addLinkToDelete(model);
      addLinkToResetPassword(model);
    } else if (isLoggedInOperator(model)) {
      addLinkToChangePassword(model);
    }
    return model;
  }

  private void addLinkToDetails(OperatorModel model) {
    model.add(linkTo(methodOn(OperatorController.class).details(model.getId())).withRel("details"));
  }

  private void addLinkToCreate(OperatorModel model) {
    model.add(linkTo(methodOn(OperatorController.class).create(model)).withRel("create"));
  }

  private void addLinkToUpdate(OperatorModel model) {
    model.add(
        linkTo(methodOn(OperatorController.class).update(model, model.getId())).withRel("update"));
  }

  private void addLinkToChangePassword(OperatorModel model) {
    model.add(
        linkTo(methodOn(OperatorController.class).changePassword(null, model.getId())).withRel("changePassword"));
  }

  private void addLinkToResetPassword(OperatorModel model) {
    model.add(
        linkTo(methodOn(OperatorController.class).resetPassword(null, model.getId())).withRel("resetPassword"));
  }

  private void addLinkToDelete(OperatorModel model) {
    model.add(
        linkTo(methodOn(OperatorController.class).delete(model.getId())).withRel("delete"));
  }


  private boolean isLoggedInOperator(OperatorModel model) {
    return SecurityContextHolder.getContext().getAuthentication().getName()
        .equals(model.getUsername());
  }

  private boolean isAdminOrSuperAdmin() {
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
