package com.apiservice.model.purchase;

import static com.apiservice.utils.pagination.Operations.dateBetween;
import static com.apiservice.utils.pagination.Operations.dateGreaterThanOrEqual;
import static com.apiservice.utils.pagination.Operations.dateLessThanOrEqual;
import static com.apiservice.utils.pagination.Operations.equal;
import static com.apiservice.utils.pagination.Operations.like;
import static com.apiservice.utils.pagination.specification.PropertySpecification.joinedQuery;

import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.utils.pagination.QueryBuilder;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class PurchaseRecordQueryBuilder implements QueryBuilder<CarPurchaseRecord> {

  private final PurchaseRecordFilter filter;

  private Specification<CarPurchaseRecord> queryForChassisNo() {
    return joinedQuery("car", "chassisNo", like, filter.getChassisNo());
  }

  private Specification<CarPurchaseRecord> queryForPurchaseType() {
    return joinedQuery("purchaseRecord", "purchaseType", equal, filter.getPurchaseType());
  }

  private Specification<CarPurchaseRecord> queryForPurchaseDate() {
    if (Objects.isNull(filter.getPurchaseDateFrom())) {
      return joinedQuery(
          "purchaseRecord", "purchaseDate", dateLessThanOrEqual, filter.getPurchaseDateTo());
    } else if (Objects.isNull(filter.getPurchaseDateTo())) {
      return joinedQuery(
          "purchaseRecord", "purchaseDate", dateGreaterThanOrEqual, filter.getPurchaseDateFrom());
    } else {
      return joinedQuery(
          "purchaseRecord",
          "purchaseDate",
          dateBetween,
          filter.getPurchaseDateFrom(),
          filter.getPurchaseDateTo());
    }
  }

  @Override
  public Specification<CarPurchaseRecord> buildQuery() {
    return queryForChassisNo().and(queryForPurchaseType()).and(queryForPurchaseDate());
  }
}
