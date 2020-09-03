package com.apiservice.model.car;

import static com.apiservice.utils.pagination.PropertySpecificationOperator.equal;
import static com.apiservice.utils.pagination.PropertySpecificationOperator.like;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.utils.pagination.PropertySpecification;
import com.apiservice.utils.pagination.QueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class CarQueryBuilder implements QueryBuilder<Car> {

  private final CarCriteria criteria;

  private Specification<Car> queryForChassisNo() {
    return PropertySpecification.<Car>query(
        "chassisNo", like, criteria.getChassisNo());
  }

  private Specification<Car> queryForDraft() {
    return PropertySpecification.<Car>query(
        "draft", equal, criteria.isDraft());
  }

  @Override
  public Specification<Car> buildQuery() {
    return queryForChassisNo().and(queryForDraft());
  }
}
