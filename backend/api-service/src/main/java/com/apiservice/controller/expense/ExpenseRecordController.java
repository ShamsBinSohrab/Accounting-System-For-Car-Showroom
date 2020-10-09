package com.apiservice.controller.expense;

import com.apiservice.entity.tenant.expense.ExpenseRecord;
import com.apiservice.model.expense.ExpenseRecordFilter;
import com.apiservice.model.expense.ExpenseRecordModel;
import com.apiservice.model.expense.ExpenseRecordModelAssembler;
import com.apiservice.service.expense.ExpenseRecordService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ExpenseRecordController {

  private final ExpenseRecordService expenseRecordService;
  private final ExpenseRecordModelAssembler modelAssembler;

  @GetMapping("/expenseRecords")
  public CollectionModel<ExpenseRecordModel> expenseRecords(
      ExpenseRecordFilter filter, Pageable pageable) {
    return modelAssembler
        .toCollectionModel(expenseRecordService.getAllWithPaginationAndFilter(filter, pageable));
  }

  @GetMapping("/expenseRecords/{id}")
  public ExpenseRecordModel expenseRecord(@PathVariable("id") long id) {
    return modelAssembler.toModel(expenseRecordService.getById(id));
  }

  @PostMapping("/expenseRecords")
  @ResponseStatus(HttpStatus.CREATED)
  public ExpenseRecordModel create(@RequestBody @Valid ExpenseRecordModel expenseRecordModel) {
    final ExpenseRecord expenseRecord = expenseRecordModel.toEntity();
    return modelAssembler.toModel(expenseRecordService.save(expenseRecord));
  }

  @PutMapping("/expenseRecords/{id}")
  public ExpenseRecordModel update(
      @PathVariable("id") long id, @RequestBody @Valid ExpenseRecordModel expenseRecordModel) {
    final ExpenseRecord expenseRecord = expenseRecordService.getById(id);
    expenseRecordModel.updateEntity(expenseRecord);
    return modelAssembler.toModel(expenseRecordService.save(expenseRecord));
  }

  @DeleteMapping("/expenseRecords/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") long id) {
    expenseRecordService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
