package com.apiservice.controller.company;

import com.apiservice.entity.master.company.Company;
import com.apiservice.model.company.CompanyFilter;
import com.apiservice.model.company.CompanyModel;
import com.apiservice.model.company.CompanyModel.CompanyTokenResponse;
import com.apiservice.service.company.CompanyService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/companies")
  public List<CompanyModel> list(CompanyFilter filter, Pageable pageable) {
    return companyService.getAllWithPaginationAndFilter(filter,pageable).stream()
            .map(CompanyModel::from)
            .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/companies/{id}")
  public CompanyModel details(@PathVariable long id) {
    final Company company = companyService.getCompanyById(id);
    return CompanyModel.from(company);
  }

  @PutMapping("/companies/{id}")
  public CompanyModel update(@PathVariable long id,@RequestBody @Valid CompanyModel model) {
    final Company company = companyService.getCompanyById(id);
    final Company updatedCompany = model.updateEntity(company);
    companyService.save(updatedCompany);
    return CompanyModel.from(updatedCompany);
  }
}
