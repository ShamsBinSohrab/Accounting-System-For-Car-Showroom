package com.migrationservice.controller;

import com.migrationservice.entity.Company;
import com.migrationservice.utils.TenantMigrationsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController {

  private final TenantMigrationsUtils tenantMigrationsUtils;

  @PostMapping("internal/createTenant")
  public ResponseEntity<Void> createTenant(@RequestBody Company company) {

    return ResponseEntity.ok().build();
  }

}
