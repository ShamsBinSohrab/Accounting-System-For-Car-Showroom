package com.apiservice.utils.pagination.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
class LikeValueSpecification<T> implements Specification<T> {

  private final String joinPropertyName;
  private final String propertyName;
  private final String value;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    Expression<String> expression = StringUtils.isNotBlank(joinPropertyName)
        ? root.join(joinPropertyName).get(propertyName)
        : root.get(propertyName);
    return builder.like(expression, StringUtils.appendIfMissing(value, "%"));
  }
}