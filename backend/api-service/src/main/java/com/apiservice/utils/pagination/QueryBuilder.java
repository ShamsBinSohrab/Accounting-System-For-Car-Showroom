package com.apiservice.utils.pagination;

import org.springframework.data.jpa.domain.Specification;

public interface QueryBuilder<T> {
  Specification<T> buildQuery();
}