package com.apiservice.utils.pagination;

import java.util.Arrays;

public enum PropertySpecificationOperator {
  equal,
  dateGreaterThanOrEqual,
  dateLessThanOrEqual,
  dateBetween,
  graterThanOrEqual;

  public boolean requiresOneOperand() {
    return Arrays
        .asList(equal, dateGreaterThanOrEqual, dateLessThanOrEqual, graterThanOrEqual)
        .contains(this);
  }

  public boolean requiresTwoOperand() {
    return this == dateBetween;
  }
}
