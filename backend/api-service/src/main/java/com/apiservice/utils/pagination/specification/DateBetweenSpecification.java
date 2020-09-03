package com.apiservice.utils.pagination.specification;

import java.time.ZonedDateTime;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
class DateBetweenSpecification<T> implements Specification<T> {

  private final String propertyName;
  private final ZonedDateTime from;
  private final ZonedDateTime to;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    return builder.between(root.get(propertyName), from, to);
  }
}