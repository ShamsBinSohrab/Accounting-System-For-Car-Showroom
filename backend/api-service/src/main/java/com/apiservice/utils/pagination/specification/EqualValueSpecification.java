package com.apiservice.utils.pagination.specification;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
class EqualValueSpecification<T> implements Specification<T> {

  private final String joinPropertyName;
  private final String propertyName;
  private final Object value;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    final From<T, T> from = isNotBlank(joinPropertyName) ? root.join(joinPropertyName) : root;
    final Class<?> type = from.get(propertyName).getJavaType();
    if (type.equals(Integer.class) || type.equals(int.class)) {
      return builder.equal(from.<Integer>get(propertyName), value);
    } else if (type.equals(Long.class) || type.equals(long.class)) {
      return builder.equal(from.<Long>get(propertyName), value);
    } else if (type.equals(Short.class) || type.equals(short.class)) {
      return builder.equal(from.<Short>get(propertyName), value);
    } else if (type.equals(Double.class) || type.equals(double.class)) {
      return builder.equal(from.<Double>get(propertyName), value);
    } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
      return builder.equal(from.<Boolean>get(propertyName), value);
    } else {
      return builder.equal(from.get(propertyName), value);
    }
  }
}
