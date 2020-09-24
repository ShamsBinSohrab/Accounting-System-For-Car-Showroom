package com.apiservice.model.expense;

import com.apiservice.enums.expense.ExpenseType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class ExpenseRecordFilter {
    private String recipient;
    private ExpenseType expenseType;
    private BigDecimal amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime expenseDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime expenseDateTo;
}
