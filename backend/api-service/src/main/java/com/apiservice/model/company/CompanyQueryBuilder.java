package com.apiservice.model.company;

import static com.apiservice.utils.pagination.Operations.equal;
import static com.apiservice.utils.pagination.Operations.like;
import static com.apiservice.utils.pagination.specification.PropertySpecification.query;

import com.apiservice.entity.master.company.Company;
import com.apiservice.utils.pagination.QueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class CompanyQueryBuilder implements QueryBuilder<Company> {

  private final CompanyFilter filter;

  private Specification<Company> queryForCompanyName() {
    return query("companyName", like, filter.getCompanyName());
  }

  private Specification<Company> queryForActive() {
    return query("active", equal, filter.getActive());
  }

  @Override
  public Specification<Company> buildQuery() {
    return queryForCompanyName()
        .and(queryForActive());
  }
}
