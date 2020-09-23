package com.apiservice.repository.expense;

import com.apiservice.entity.tenant.expense.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long>,
        JpaSpecificationExecutor<ExpenseRecord> {
}
