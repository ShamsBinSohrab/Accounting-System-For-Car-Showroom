package com.apiservice.model.expense;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.authentication.AuthenticationService;
import com.apiservice.authentication.Scopes;
import com.apiservice.controller.expense.ExpenseRecordController;
import com.apiservice.entity.tenant.expense.ExpenseRecord;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpenseRecordModelAssembler implements
    RepresentationModelAssembler<ExpenseRecord, ExpenseRecordModel> {

  private final ModelMapper mapper;
  private final HttpServletRequest request;
  private final AuthenticationService authService;

  @Override
  public ExpenseRecordModel toModel(ExpenseRecord expenseRecord) {
    final ExpenseRecordModel model = mapper.map(expenseRecord, ExpenseRecordModel.class);
    final List<Scopes> scopes = authService.extractScopesFromToken(request);
    if(scopes.contains("EXPENSE_RECORD_READ"))
      addLinkToDetails(model);
    if(scopes.contains("EXPENSE_RECORD_WRITE"))
    {
      addLinkToCreate(model);
      addLinkToUpdate(model);
      addLinkToDelete(model);
    }
    return model;
  }

  private void addLinkToDetails(ExpenseRecordModel model) {
    model.add(linkTo(methodOn(ExpenseRecordController.class).expenseRecord(model.getId()))
        .withRel("details"));
  }

  private void addLinkToCreate(ExpenseRecordModel model) {
    model.add(linkTo(methodOn(ExpenseRecordController.class).create(model))
        .withRel("create"));
  }


  private void addLinkToUpdate(ExpenseRecordModel model) {
    model.add(linkTo(methodOn(ExpenseRecordController.class).update(model.getId(), model))
        .withRel("update"));
  }

  private void addLinkToDelete(ExpenseRecordModel model) {
    model.add(linkTo(methodOn(ExpenseRecordController.class).delete(model.getId()))
        .withRel("delete"));
  }
}
