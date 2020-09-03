package com.apiservice.utils.pagination.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
class GraterThanOrEqualSpecification<T> implements Specification<T> {

  private final String propertyName;
  private final Object value;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    Class<?> type = root.get(propertyName).getJavaType();
    if (type.equals(Integer.class) || type.equals(int.class)) {
      return builder.greaterThanOrEqualTo(root.get(propertyName), (Integer) value);
    } else if (type.equals(Long.class) || type.equals(long.class)) {
      return builder.greaterThanOrEqualTo(root.get(propertyName), (Long) value);
    } else if (type.equals(Short.class) || type.equals(short.class)) {
      return builder.greaterThanOrEqualTo(root.get(propertyName), (Short) value);
    } else if (type.equals(Double.class) || type.equals(double.class)) {
      return builder.greaterThanOrEqualTo(root.get(propertyName), (Double) value);
    } else {
      throw new IllegalArgumentException(value + " is not a number");
    }
  }
}
