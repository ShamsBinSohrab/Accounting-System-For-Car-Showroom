package com.apiservice.service.expense;

import com.apiservice.entity.tenant.expense.ExpenseRecord;
import com.apiservice.model.expense.ExpenseRecordFilter;
import com.apiservice.model.expense.ExpenseRecordQueryBuilder;
import com.apiservice.repository.expense.ExpenseRecordRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import com.apiservice.utils.pagination.PaginationService;
import com.apiservice.utils.pagination.QueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseRecordService {

    private final ExpenseRecordRepository expenseRecordRepository;
    private final PaginationService<ExpenseRecord> paginationService;

    @Transactional(readOnly = true)
    public Page<ExpenseRecord> getAllWithPaginationAndFilter(
            ExpenseRecordFilter filter, Pageable pageable) {
        final QueryBuilder<ExpenseRecord> queryBuilder = new ExpenseRecordQueryBuilder(filter);
        return paginationService.paginate(expenseRecordRepository, queryBuilder, pageable);
    }

    @Transactional(readOnly = true)
    public ExpenseRecord getById(long id) {
        return expenseRecordRepository
                .findById(id)
                .orElseThrow(() -> EntityNotFoundException.of(ExpenseRecord.class, id));
    }

    @Transactional
    public void save(ExpenseRecord expenseRecord) {
        expenseRecordRepository.save(expenseRecord);
    }

    @Transactional
    public void delete(long id) {
        expenseRecordRepository.deleteById(id);
    }

}
