package com.migrationservice.controller;

import com.migrationservice.entity.Company;
import com.migrationservice.utils.TenantMigrationsUtils;
import java.sql.SQLException;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyControllerInternal {

  private final TenantMigrationsUtils tenantMigrationsUtils;

  @PostMapping("/internal/createTenant")
  public String createTenant(@RequestBody Company company)
      throws LiquibaseException, SQLException {
    return tenantMigrationsUtils.createNewCompany(company);
  }

}
