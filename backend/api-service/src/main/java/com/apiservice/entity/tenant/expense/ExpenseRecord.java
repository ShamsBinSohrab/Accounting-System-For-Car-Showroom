package com.apiservice.entity.tenant.expense;

import com.apiservice.entity.tenant.Auditable;
import com.apiservice.enums.expense.ExpenseType;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "expense_record")
public class ExpenseRecord extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "expense_date", nullable = false)
    private ZonedDateTime expenseDate;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Digits(integer = 10, fraction = 2)
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type")
    private ExpenseType expenseType;
}
