package com.apiservice.utils.pagination;

import java.util.Arrays;

public enum PropertySpecificationOperator {
  equal,
  like,
  dateGreaterThanOrEqual,
  dateLessThanOrEqual,
  dateBetween,
  graterThanOrEqual;

  public boolean requiresOneOperand() {
    return Arrays
        .asList(equal, like, dateGreaterThanOrEqual, dateLessThanOrEqual, graterThanOrEqual)
        .contains(this);
  }

  public boolean requiresTwoOperand() {
    return this == dateBetween;
  }
}
