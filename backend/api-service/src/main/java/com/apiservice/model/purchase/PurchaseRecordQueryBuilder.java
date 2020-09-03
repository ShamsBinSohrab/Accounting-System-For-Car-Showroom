package com.apiservice.model.purchase;

import static com.apiservice.utils.pagination.PropertySpecificationOperator.dateBetween;
import static com.apiservice.utils.pagination.PropertySpecificationOperator.dateLessThanOrEqual;
import static com.apiservice.utils.pagination.PropertySpecificationOperator.equal;
import static com.apiservice.utils.pagination.PropertySpecificationOperator.like;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.entity.tenant.purchase.PurchaseRecord;
import com.apiservice.utils.pagination.JoinedPropertySpecification;
import com.apiservice.utils.pagination.PropertySpecificationOperator;
import com.apiservice.utils.pagination.QueryBuilder;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class PurchaseRecordQueryBuilder implements QueryBuilder<CarPurchaseRecord> {

  private final PurchaseRecordCriteria criteria;

  private Specification<CarPurchaseRecord> queryForChassisNo() {
    return JoinedPropertySpecification.<CarPurchaseRecord, Car>joinedQuery(
        "car", "chassisNo", like, criteria.getChassisNo());
  }

  private Specification<CarPurchaseRecord> queryForPurchaseType() {
    return JoinedPropertySpecification.<CarPurchaseRecord, PurchaseRecord>joinedQuery(
        "purchaseRecord", "purchaseType", equal, criteria.getPurchaseType());
  }

  private Specification<CarPurchaseRecord> queryForPurchaseDate() {
    if (Objects.isNull(criteria.getPurchaseDateFrom())) {
      return JoinedPropertySpecification.<CarPurchaseRecord, PurchaseRecord>joinedQuery(
          "purchaseRecord", "purchaseDate", dateLessThanOrEqual, criteria.getPurchaseDateTo());
    } else if (Objects.isNull(criteria.getPurchaseDateTo())) {
      return JoinedPropertySpecification.<CarPurchaseRecord, PurchaseRecord>joinedQuery(
          "purchaseRecord",
          "purchaseDate",
          PropertySpecificationOperator.dateGreaterThanOrEqual,
          criteria.getPurchaseDateFrom());
    } else {
      return JoinedPropertySpecification.<CarPurchaseRecord, PurchaseRecord>joinedQuery(
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
