package com.apiservice.model.expense;

import com.apiservice.entity.tenant.expense.ExpenseRecord;
import com.apiservice.utils.pagination.QueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.apiservice.utils.pagination.Operations.*;
import static com.apiservice.utils.pagination.specification.PropertySpecification.query;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class ExpenseRecordQueryBuilder implements QueryBuilder<ExpenseRecord> {

    private final ExpenseRecordFilter filter;

    private Specification<ExpenseRecord> queryForRecipient() {
        return query("recipient", like, filter.getRecipient());
    }

    private Specification<ExpenseRecord> queryForExpenseType() { return query("expenseType", equal, filter.getExpenseType()); }

    private Specification<ExpenseRecord> queryForAmount() { return query("amount", equal, filter.getAmount()); }

    private Specification<ExpenseRecord> queryForExpenseDate() {
        if (isNull(filter.getExpenseDateFrom())) {
            return query(
                    "expenseDate", dateLessThanOrEqual, filter.getExpenseDateTo());
        } else if (isNull(filter.getExpenseDateTo())) {
            return query(
                    "expenseDate", dateGreaterThanOrEqual, filter.getExpenseDateFrom());
        } else {
            return query(
                    "expenseDate",
                    dateBetween,
                    filter.getExpenseDateFrom(),
                    filter.getExpenseDateTo());
        }
    }
    @Override
    public Specification<ExpenseRecord> buildQuery() {
        return queryForRecipient().and(queryForExpenseType()).and(queryForAmount()).and(queryForExpenseDate());
    }
}
