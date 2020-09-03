package com.apiservice.utils.pagination;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class PropertySpecification<T> {

  @With private String propertyName;
  @With private PropertySpecificationOperator operator;
  @With private Object value1;
  @With private Object value2;

  /**
   * Create a {@link Specification} based on the given parameters.
   *
   * @param propertyName The property to filter
   * @param operator The filter operation
   * @param v1 The filter value
   * @param <T> Type.
   * @return
   */
  public static <T> Specification<T> query(String propertyName, PropertySpecificationOperator operator, Object v1) {
    if (operator.requiresOneOperand()) {
      return new PropertySpecification<T>()
          .withPropertyName(propertyName)
          .withOperator(operator)
          .withValue1(v1)
          .build();
    }
    throw new IllegalArgumentException(operator.name() + " requires 1 operand");
  }

  /**
   * Create a {@link Specification} based on the given parameters.
   *
   * @param propertyName The property to filter.
   * @param operator The filter operation.
   * @param v1 The first filter value.
   * @param v2 The second filter value
   * @param <T> Type.
   * @return
   */
  public static <T> Specification<T> query(
      String propertyName, PropertySpecificationOperator operator, Object v1, Object v2) {
    if (operator.requiresTwoOperand()) {
      return new PropertySpecification<T>()
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
        case equal -> new EqualValueSpecification<T>(propertyName, value1);
        case like -> new LikeValueSpecification<T>(propertyName, (String) value1);
        case dateGreaterThanOrEqual -> new DateGreaterThanOrEqual<T>(propertyName,
            (ZonedDateTime) value1);
        case dateLessThanOrEqual -> new DateLessThanOrEqual<T>(propertyName,
            (ZonedDateTime) value1);
        case dateBetween -> new DateBetweenSpecification<T>(
            propertyName, (ZonedDateTime) value1, (ZonedDateTime) value2);
        case graterThanOrEqual -> new GraterThanOrEqualSpecification<T>(propertyName, value1);
      };
    }
    return Specification.where(null);
  }

  @RequiredArgsConstructor
  private static class EqualValueSpecification<T> implements Specification<T> {

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

  @RequiredArgsConstructor
  private static class LikeValueSpecification<T> implements Specification<T> {

    private final String propertyName;
    private final String value;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      return builder.like(root.get(propertyName), StringUtils.appendIfMissing(value, "%"));
    }
  }

  @RequiredArgsConstructor
  private static class DateBetweenSpecification<T> implements Specification<T> {

    private final String propertyName;
    private final ZonedDateTime from;
    private final ZonedDateTime to;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      return builder.between(root.get(propertyName), from, to);
    }
  }

  @RequiredArgsConstructor
  private static class DateGreaterThanOrEqual<T> implements Specification<T> {

    private final String propertyName;
    private final ZonedDateTime date;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      return builder.greaterThanOrEqualTo(root.get(propertyName), date);
    }
  }

  @RequiredArgsConstructor
  private static class DateLessThanOrEqual<T> implements Specification<T> {

    private final String propertyName;
    private final ZonedDateTime date;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      return builder.lessThanOrEqualTo(root.get(propertyName), date);
    }
  }

  @RequiredArgsConstructor
  private static class GraterThanOrEqualSpecification<T> implements Specification<T> {

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
}

