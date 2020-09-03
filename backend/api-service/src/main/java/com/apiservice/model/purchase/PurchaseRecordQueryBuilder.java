package com.apiservice.model.purchase;

import static com.apiservice.utils.pagination.PropertySpecification.Operations.dateBetween;
import static com.apiservice.utils.pagination.PropertySpecification.Operations.dateGreaterThanOrEqual;
import static com.apiservice.utils.pagination.PropertySpecification.Operations.dateLessThanOrEqual;
import static com.apiservice.utils.pagination.PropertySpecification.Operations.equal;
import static com.apiservice.utils.pagination.PropertySpecification.Operations.like;
import static com.apiservice.utils.pagination.PropertySpecification.joinedQuery;

import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.utils.pagination.QueryBuilder;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class PurchaseRecordQueryBuilder implements QueryBuilder<CarPurchaseRecord> {

  private final PurchaseRecordCriteria criteria;

  private Specification<CarPurchaseRecord> queryForChassisNo() {
    return joinedQuery("car", "chassisNo", like, criteria.getChassisNo());
  }

  private Specification<CarPurchaseRecord> queryForPurchaseType() {
    return joinedQuery("purchaseRecord", "purchaseType", equal, criteria.getPurchaseType());
  }

  private Specification<CarPurchaseRecord> queryForPurchaseDate() {
    if (Objects.isNull(criteria.getPurchaseDateFrom())) {
      return joinedQuery(
          "purchaseRecord", "purchaseDate", dateLessThanOrEqual, criteria.getPurchaseDateTo());
    } else if (Objects.isNull(criteria.getPurchaseDateTo())) {
      return joinedQuery(
          "purchaseRecord", "purchaseDate", dateGreaterThanOrEqual, criteria.getPurchaseDateFrom());
    } else {
      return joinedQuery(
          "purchaseRecord",
          "purchaseDate",
          dateBetween,
          criteria.getPurchaseDateFrom(),
          criteria.getPurchaseDateTo());
    }
  }

  @Override
  public Specification<CarPurchaseRecord> buildQuery() {
    return queryForChassisNo().and(queryForPurchaseType()).and(queryForPurchaseDate());
  }
}
