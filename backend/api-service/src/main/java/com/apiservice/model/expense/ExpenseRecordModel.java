package com.apiservice.model.expense;

import com.apiservice.entity.tenant.expense.ExpenseRecord;
import com.apiservice.enums.expense.ExpenseType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

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
        return mapper.map(expenseRecord, ExpenseRecordModel.class);
    }

    public ExpenseRecord toEntity() {
        return mapper.map(this, ExpenseRecord.class);
    }

    public void updateEntity(ExpenseRecord expenseRecord) {
        final long id = expenseRecord.getId();
        mapper.map(this, expenseRecord);
        expenseRecord.setId(id);
    }
}
