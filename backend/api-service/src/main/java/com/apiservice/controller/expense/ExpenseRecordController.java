package com.apiservice.controller.expense;

import com.apiservice.entity.tenant.expense.ExpenseRecord;
import com.apiservice.model.expense.ExpenseRecordFilter;
import com.apiservice.model.expense.ExpenseRecordModel;
import com.apiservice.service.expense.ExpenseRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ExpenseRecordController {
    private final ExpenseRecordService expenseRecordService;

    @GetMapping("/expenseRecords")
    public List<ExpenseRecordModel> expenseRecords(
            ExpenseRecordFilter filter, Pageable pageable) {
        return expenseRecordService.getAllWithPaginationAndFilter(filter, pageable).stream()
                .map(ExpenseRecordModel::toModel)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/expenseRecords/{id}")
    public ExpenseRecordModel expenseRecord(@PathVariable("id") long id) {
        final ExpenseRecord expenseRecord = expenseRecordService.getById(id);
        return ExpenseRecordModel.toModel(expenseRecord);
    }

    @PostMapping("/expenseRecords")
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseRecordModel create(@RequestBody @Valid ExpenseRecordModel expenseRecordModel) {
        final ExpenseRecord expenseRecord = expenseRecordModel.toEntity();
        expenseRecordService.save(expenseRecord);
        return ExpenseRecordModel.toModel(expenseRecord);
    }

    @PutMapping("/expenseRecords/{id}")
    public ExpenseRecordModel update(
            @PathVariable("id") long id, @RequestBody @Valid ExpenseRecordModel expenseRecordModel) {
        final ExpenseRecord expenseRecordToUpdate = expenseRecordService.getById(id);
        final ExpenseRecord expenseRecord = expenseRecordModel.updateEntity(expenseRecordToUpdate);
        expenseRecordService.save(expenseRecord);
        return ExpenseRecordModel.toModel(expenseRecord);
    }

    @DeleteMapping("/expenseRecords/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        expenseRecordService.delete(id);
    }
}
