package com.apiservice.model.company;

import lombok.Data;

@Data
public class CompanyFilter {

  private String companyName;
  private Boolean active;
}
