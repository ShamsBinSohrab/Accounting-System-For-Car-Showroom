package com.apiservice.utils.pagination.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
class EqualValueSpecification<T> implements Specification<T> {

  private final String propertyName;
  private final Object value;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    Class<?> type = root.get(propertyName).getJavaType();
    if (type.equals(Integer.class) || type.equals(int.class)) {
      return builder.equal(root.<Integer>get(propertyName), value);
    } else if (type.equals(Long.class) || type.equals(long.class)) {
      return builder.equal(root.<Long>get(propertyName), value);
    } else if (type.equals(Short.class) || type.equals(short.class)) {
      return builder.equal(root.<Short>get(propertyName), value);
    } else if (type.equals(Double.class) || type.equals(double.class)) {
      return builder.equal(root.<Double>get(propertyName), value);
    } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
      return builder.equal(root.<Boolean>get(propertyName), value);
    } else {
      return builder.equal(root.get(propertyName), value);
    }
  }
}
