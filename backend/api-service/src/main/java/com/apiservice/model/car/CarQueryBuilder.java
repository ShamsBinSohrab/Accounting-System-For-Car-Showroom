package com.apiservice.model.car;

import static com.apiservice.utils.pagination.Operations.equal;
import static com.apiservice.utils.pagination.Operations.like;
import static com.apiservice.utils.pagination.specification.PropertySpecification.joinedQuery;
import static com.apiservice.utils.pagination.specification.PropertySpecification.query;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.utils.pagination.QueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class CarQueryBuilder implements QueryBuilder<Car> {

  private final CarCriteria criteria;

  private Specification<Car> queryForChassisNo() {
    return query("chassisNo", like, criteria.getChassisNo());
  }

  private Specification<Car> queryForDraft() {
    return query("draft", equal, criteria.isDraft());
  }

  private Specification<Car> queryForMake() {
    return joinedQuery("carDetails", "make", equal, criteria.getMake());
  }

  private Specification<Car> queryForName() {
    return joinedQuery("carDetails", "name", like, criteria.getName());
  }

  private Specification<Car> queryForType() {
    return joinedQuery("carDetails", "type", equal, criteria.getType());
  }

  private Specification<Car> queryForModelYear() {
    return joinedQuery("carDetails", "modelYear", equal, criteria.getModelYear());
  }

  private Specification<Car> queryForColor() {
    return joinedQuery("carDetails", "color", like, criteria.getColor());
  }

  @Override
  public Specification<Car> buildQuery() {
    return queryForChassisNo()
        .and(queryForDraft())
        .and(queryForMake())
        .and(queryForName())
        .and(queryForType())
        .and(queryForModelYear())
        .and(queryForColor());
  }
}
