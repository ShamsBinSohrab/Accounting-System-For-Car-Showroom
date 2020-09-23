package com.apiservice.model.expense;

import com.apiservice.controller.expense.ExpenseRecordController;
import com.apiservice.entity.tenant.expense.ExpenseRecord;
import com.apiservice.enums.expense.ExpenseType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
public class ExpenseRecordModel extends RepresentationModel<ExpenseRecordModel> {

    private static final ModelMapper mapper = new ModelMapper();

    private Long id;
    private ZonedDateTime expenseDate;
    private String recipient;
    private BigDecimal amount;
    private String notes;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    public static ExpenseRecordModel toModel(ExpenseRecord expenseRecord) {
        return mapper.map(expenseRecord, ExpenseRecordModel.class).addLinks();
    }

    public ExpenseRecord toEntity() {
        return mapper.map(this, ExpenseRecord.class);
    }

    public ExpenseRecord updateEntity(ExpenseRecord source) {
        ExpenseRecord expenseRecord = mapper.map(this, ExpenseRecord.class);
        expenseRecord.setId(source.getId());
        return expenseRecord;
    }

    private ExpenseRecordModel addLinks() {
        add(linkTo(methodOn(ExpenseRecordController.class).expenseRecord(id)).withSelfRel());
        return this;
    }
}
