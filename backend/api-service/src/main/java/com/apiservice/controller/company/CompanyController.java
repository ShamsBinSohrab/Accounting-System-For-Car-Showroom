package com.apiservice.controller.company;

import com.apiservice.entity.master.company.Company;
import com.apiservice.model.company.CompanyModel;
import com.apiservice.model.company.CompanyModel.CompanyTokenResponse;
import com.apiservice.service.company.CompanyService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/v1")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class CompanyController {

  private final CompanyService companyService;


  @GetMapping("/companies/{id}/token")
  public CompanyTokenResponse getCompanyToken(@PathVariable("id") long id) {
    return Optional.of(companyService.getCompanyToken(id))
        .map(CompanyTokenResponse::of)
        .orElseThrow(IllegalArgumentException::new);
  }

  @PostMapping("/companies")
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyModel create(@RequestBody @Valid CompanyModel model) {
    final Company company = model.toEntity();
    return CompanyModel.from(companyService.create(company));
  }
}
