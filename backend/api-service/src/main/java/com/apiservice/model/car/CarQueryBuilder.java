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

  private final CarFilter filter;

  private Specification<Car> queryForChassisNo() {
    return query("chassisNo", like, filter.getChassisNo());
  }

  private Specification<Car> queryForDraft() {
    return query("draft", equal, filter.isDraft());
  }

  private Specification<Car> queryForMake() {
    return joinedQuery("details", "make", equal, filter.getMake());
  }

  private Specification<Car> queryForName() {
    return joinedQuery("details", "name", like, filter.getName());
  }

  private Specification<Car> queryForType() {
    return joinedQuery("details", "type", equal, filter.getType());
  }

  private Specification<Car> queryForModelYear() {
    return joinedQuery("details", "modelYear", equal, filter.getModelYear());
  }

  private Specification<Car> queryForColor() {
    return joinedQuery("details", "color", like, filter.getColor());
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
