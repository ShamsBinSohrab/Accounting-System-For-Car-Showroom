package com.apiservice.utils.pagination.specification;

import com.apiservice.utils.pagination.Operations;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertySpecification<T> {

  @With private String joinPropertyName;
  @With private String propertyName;
  @With private Operations operation;
  @With private Object value1;
  @With private Object value2;

  /**
   * Create a {@link Specification} based on the given parameters.
   *
   * @param propertyName The property to filter
   * @param operation The filter operation
   * @param v1 The filter value
   * @param <T> Type.
   * @return
   */
  public static <T> Specification<T> query(String propertyName, Operations operation, Object v1) {
    if (operation.requiresOneOperand()) {
      return new PropertySpecification<T>()
          .withPropertyName(propertyName)
          .withOperation(operation)
          .withValue1(v1)
          .build();
    }
    throw new IllegalArgumentException(operation.name() + " requires 1 operand");
  }

  /**
   * Create a {@link Specification} based on the given parameters.
   *
   * @param joinPropertyName The property to join.
   * @param propertyName The property to filter.
   * @param operation The filter operation.
   * @param v1 The first filter value.
   * @param <T> Type.
   * @return
   */
  public static <T> Specification<T> joinedQuery(String joinPropertyName, String propertyName, Operations operation, Object v1) {
    if (operation.requiresOneOperand()) {
      return new PropertySpecification<T>()
          .withJoinPropertyName(joinPropertyName)
          .withPropertyName(propertyName)
          .withOperation(operation)
          .withValue1(v1)
          .build();
    }
    throw new IllegalArgumentException(operation.name() + " requires 1 operand");
  }

  /**
   * Create a {@link Specification} based on the given parameters.
   *
   * @param propertyName The property to filter.
   * @param operation The filter operation.
   * @param v1 The first filter value.
   * @param v2 The second filter value
   * @param <T> Type.
   * @return
   */
  public static <T> Specification<T> query(
      String propertyName, Operations operation, Object v1, Object v2) {
    if (operation.requiresTwoOperand()) {
      return new PropertySpecification<T>()
          .withPropertyName(propertyName)
          .withOperation(operation)
          .withValue1(v1)
          .withValue2(v2)
          .build();
    }
    throw new IllegalArgumentException(operation.name() + " requires 2 operands");
  }


  /**
   * Create a {@link Specification} based on the given parameters.
   *
   * @param joinPropertyName The property to join.
   * @param propertyName The property to filter.
   * @param operation The filter operation.
   * @param v1 The first filter value.
   * @param v2 The second filter value
   * @param <T> Type.
   * @return
   */
  public static <T> Specification<T> joinedQuery(String joinPropertyName, String propertyName, Operations operation, Object v1, Object v2) {
    if (operation.requiresOneOperand()) {
      return new PropertySpecification<T>()
          .withJoinPropertyName(joinPropertyName)
          .withPropertyName(propertyName)
          .withOperation(operation)
          .withValue1(v1)
          .withValue2(v2)
          .build();
    }
    throw new IllegalArgumentException(operation.name() + " requires 1 operand");
  }

  private Specification<T> build() {
    if (Objects.nonNull(value1)) {
      return switch (operation) {
        case equal -> new EqualValueSpecification<>(joinPropertyName, propertyName, value1);
        case like -> new LikeValueSpecification<>(joinPropertyName, propertyName, (String) value1);
        case dateGreaterThanOrEqual ->
            new DateGreaterThanOrEqual<>(joinPropertyName, propertyName, (ZonedDateTime) value1);
        case dateLessThanOrEqual ->
            new DateLessThanOrEqual<>(joinPropertyName, propertyName, (ZonedDateTime) value1);
        case dateBetween ->
            new DateBetweenSpecification<>(joinPropertyName, propertyName, (ZonedDateTime) value1, (ZonedDateTime) value2);
        case graterThanOrEqual ->
            new GraterThanOrEqualSpecification<>(joinPropertyName, propertyName, value1);
      };
    }
    return Specification.where(null);
  }
}

