package com.apiservice.utils.pagination;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedPropertySpecification<T, R> {

  @With private String joinFieldName;
  @With private String propertyName;
  @With private PropertySpecificationOperator operator;
  @With private Object value1;
  @With private Object value2;

  public static <T, R> Specification<T> joinedQuery(String joinFieldName, String propertyName, PropertySpecificationOperator operator, Object v1) {
    if (operator.requiresOneOperand()) {
      return new JoinedPropertySpecification<T, R>()
          .withJoinFieldName(joinFieldName)
          .withPropertyName(propertyName)
          .withOperator(operator)
          .withValue1(v1)
          .build();
    }
    throw new IllegalArgumentException(operator.name() + " requires 1 operand");
  }

  public static <T, R> Specification<T> joinedQuery(
      String joinFieldName, String propertyName, PropertySpecificationOperator operator, Object v1, Object v2) {
    if (operator.requiresTwoOperand()) {
      return new JoinedPropertySpecification<T, R>()
          .withJoinFieldName(joinFieldName)
          .withPropertyName(propertyName)
          .withOperator(operator)
          .withValue1(v1)
          .withValue2(v2)
          .build();
    }
    throw new IllegalArgumentException(operator.name() + " requires 2 operands");
  }

  private Specification<T> build() {
    if (Objects.nonNull(value1)) {
      return switch (operator) {
        case equal -> new EqualValueSpecification<T, R>(joinFieldName, propertyName, value1);
        case like -> new LikeValueSpecification<T, R>(joinFieldName, propertyName, (String) value1);
        case dateGreaterThanOrEqual -> new DateGreaterThanOrEqual<T, R>(joinFieldName, propertyName, (ZonedDateTime) value1);
        case dateLessThanOrEqual -> new DateLessThanOrEqual<T, R>(joinFieldName, propertyName,
            (ZonedDateTime) value1);
        case dateBetween -> new DateBetweenSpecification<T, R>(
            joinFieldName, propertyName, (ZonedDateTime) value1, (ZonedDateTime) value2);
        case graterThanOrEqual -> new GraterThanOrEqualSpecification<T, R>(joinFieldName, propertyName, value1);
      };
    }
    return Specification.where(null);
  }

  @RequiredArgsConstructor
  private static class EqualValueSpecification<T, R> implements Specification<T> {

    private final String joinFieldName;
    private final String propertyName;
    private final Object value;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      final Join<T, R> join = root.join(joinFieldName);
      final Class<?> type = join.get(propertyName).getJavaType();
      if (type.equals(Integer.class) || type.equals(int.class)) {
        return builder.equal(join.<Integer>get(propertyName), value);
      } else if (type.equals(Long.class) || type.equals(long.class)) {
        return builder.equal(join.<Long>get(propertyName), value);
      } else if (type.equals(Short.class) || type.equals(short.class)) {
        return builder.equal(join.<Short>get(propertyName), value);
      } else if (type.equals(Double.class) || type.equals(double.class)) {
        return builder.equal(join.<Double>get(propertyName), value);
      } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
        return builder.equal(join.<Boolean>get(propertyName), value);
      } else {
        return builder.equal(join.get(propertyName), value);
      }
    }
  }

  @RequiredArgsConstructor
  private static class LikeValueSpecification<T, R> implements Specification<T> {

    private final String joinFieldName;
    private final String propertyName;
    private final String value;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      final Join<T, R> join = root.join(joinFieldName);
      return builder.like(join.get(propertyName), StringUtils.appendIfMissing(value, "%"));
    }
  }

  @RequiredArgsConstructor
  private static class DateBetweenSpecification<T, R> implements Specification<T> {

    private final String joinFieldName;
    private final String propertyName;
    private final ZonedDateTime from;
    private final ZonedDateTime to;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      final Join<T, R> join = root.join(joinFieldName);
      return builder.between(join.get(propertyName), from, to);
    }
  }

  @RequiredArgsConstructor
  private static class DateGreaterThanOrEqual<T, R> implements Specification<T> {

    private final String joinFieldName;
    private final String propertyName;
    private final ZonedDateTime date;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      final Join<T, R> join = root.join(joinFieldName);
      return builder.greaterThanOrEqualTo(join.get(propertyName), date);
    }
  }

  @RequiredArgsConstructor
  private static class DateLessThanOrEqual<T, R> implements Specification<T> {

    private final String joinFieldName;
    private final String propertyName;
    private final ZonedDateTime date;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      final Join<T, R> join = root.join(joinFieldName);
      return builder.lessThanOrEqualTo(join.get(propertyName), date);
    }
  }

  @RequiredArgsConstructor
  private static class GraterThanOrEqualSpecification<T, R> implements Specification<T> {
    private final String joinFieldName;
    private final String propertyName;
    private final Object value;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      final Join<T, R> join = root.join(joinFieldName);
      final Class<?> type = root.get(propertyName).getJavaType();
      if (type.equals(Integer.class) || type.equals(int.class)) {
        return builder.greaterThanOrEqualTo(join.get(propertyName), (Integer) value);
      } else if (type.equals(Long.class) || type.equals(long.class)) {
        return builder.greaterThanOrEqualTo(join.get(propertyName), (Long) value);
      } else if (type.equals(Short.class) || type.equals(short.class)) {
        return builder.greaterThanOrEqualTo(join.get(propertyName), (Short) value);
      } else if (type.equals(Double.class) || type.equals(double.class)) {
        return builder.greaterThanOrEqualTo(join.get(propertyName), (Double) value);
      } else {
        throw new IllegalArgumentException(value + " is not a number");
      }
    }
  }
}
