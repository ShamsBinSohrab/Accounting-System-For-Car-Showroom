package com.apiservice.model.car;

import static com.apiservice.utils.pagination.Operations.equal;
import static com.apiservice.utils.pagination.specification.PropertySpecification.query;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.utils.pagination.QueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class CarQueryBuilder implements QueryBuilder<Car> {

  private final CarCriteria criteria;

  private Specification<Car> queryForChassisNo() {
    return query("chassisNo", equal, criteria.getChassisNo());
  }

  private Specification<Car> queryForDraft() {
    return query("draft", equal, criteria.isDraft());
  }

  @Override
  public Specification<Car> buildQuery() {
    return queryForChassisNo().and(queryForDraft());
  }
}
