package com.apiservice.utils.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public class PaginationService<T> {

  public <R extends JpaSpecificationExecutor<T>> Page<T> paginate(
      R repo, QueryBuilder<T> qb, Pageable pageable) {
    return repo.findAll(qb.buildQuery(), pageable);
  }

  public <R extends JpaSpecificationExecutor<T>> Page<T> paginate(R repo, Pageable pageable) {
    return repo.findAll(Specification.where(null), pageable);
  }
}
