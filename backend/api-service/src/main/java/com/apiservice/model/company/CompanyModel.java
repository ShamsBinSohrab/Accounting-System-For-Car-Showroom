package com.apiservice.model.company;

import com.apiservice.entity.master.company.Company;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
public class CompanyModel {

  private static final ModelMapper mapper = new ModelMapper();

  private long id;

  @NotBlank(message = "company name can not be empty")
  @Size(min = 1, max = 100)
  private String companyName;

  private boolean active;

  public Company toEntity() {
    return mapper.map(this, Company.class);
  }

  public static CompanyModel from(Company company) {
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
