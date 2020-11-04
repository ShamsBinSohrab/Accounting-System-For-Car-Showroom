package com.apiservice.model.sell;

import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.utils.pagination.QueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.apiservice.utils.pagination.Operations.*;
import static com.apiservice.utils.pagination.specification.PropertySpecification.joinedQuery;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class CarSellRecordQueryBuilder implements QueryBuilder<CarSellRecord> {

    private final CarSellRecordFilter filter;

    private Specification<CarSellRecord> queryForChassisNo() {
        return joinedQuery("car", "chassisNo", like, filter.getChassisNo());
    }

    private Specification<CarSellRecord> queryForSellType() {
        return joinedQuery("sellType", "sellType", equal, filter.getSellType());
    }

    private Specification<CarSellRecord> queryForSellDate() {
        if (isNull(filter.getSellDateFrom())) {
            return joinedQuery(
                    "sellRecord", "sellDate", dateLessThanOrEqual, filter.getSellDateTo());
        } else if (isNull(filter.getSellDateTo())) {
            return joinedQuery(
                    "sellRecord", "sellDate", dateGreaterThanOrEqual, filter.getSellDateFrom());
        } else {
            return joinedQuery(
                    "sellRecord",
                    "sellDate",
                    dateBetween,
                    filter.getSellDateFrom(),
                    filter.getSellDateTo());
        }
    }

    @Override
    public Specification<CarSellRecord> buildQuery() {
        return queryForChassisNo().and(queryForSellType()).and(queryForSellDate());
    }
}
