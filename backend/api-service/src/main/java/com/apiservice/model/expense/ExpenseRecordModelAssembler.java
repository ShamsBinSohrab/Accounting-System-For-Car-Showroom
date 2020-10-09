package com.apiservice.model.expense;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.controller.expense.ExpenseRecordController;
import com.apiservice.entity.tenant.expense.ExpenseRecord;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpenseRecordModelAssembler implements
    RepresentationModelAssembler<ExpenseRecord, ExpenseRecordModel> {

  private final ModelMapper mapper;

  @Override
  public ExpenseRecordModel toModel(ExpenseRecord expenseRecord) {
    ExpenseRecordModel model = mapper.map(expenseRecord, ExpenseRecordModel.class);
    addLinkToDetails(model);
    addLinkToUpdate(model);
    addLinkToDelete(model);
    return model;
  }

  private void addLinkToDetails(ExpenseRecordModel model) {
    model.add(linkTo(methodOn(ExpenseRecordController.class).expenseRecord(model.getId()))
        .withRel("details"));
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
