package com.apiservice.model.company;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.apiservice.entity.master.company.Company;

@Data
public class CompanyModel {

  private static final ModelMapper mapper = new ModelMapper();

  private long id;
  private String companyName;
  private boolean active;

  public Company toEntity() {
    return mapper.map(this, Company.class);
  }

  public static CompanyModel toModel(Company company) {
    return mapper.map(company, CompanyModel.class);
  }

  @Data
  @RequiredArgsConstructor
  public static class CompanyTokenResponse {
    private final String companyToken;

    public static CompanyTokenResponse of(String token) {
      return new CompanyTokenResponse(token);
    }
  }
}
