package com.apiservice.controller.company;

import com.apiservice.entity.master.company.Company;
import com.apiservice.model.company.CompanyFilter;
import com.apiservice.model.company.CompanyModel;
import com.apiservice.model.company.CompanyModelAssembler;
import com.apiservice.model.company.CompanyTokenResponse;
import com.apiservice.service.company.CompanyService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;
  private final CompanyModelAssembler modelAssembler;


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
    return modelAssembler.toModel(companyService.create(company));
  }

  @GetMapping("/companies")
  public CollectionModel<CompanyModel> list(CompanyFilter filter, Pageable pageable) {
    return modelAssembler
        .toCollectionModel(companyService.getAllWithPaginationAndFilter(filter, pageable));
  }

  @GetMapping("/companies/{id}")
  public CompanyModel details(@PathVariable long id) {
    return modelAssembler.toModel(companyService.getCompanyById(id));
  }

  @PutMapping("/companies/{id}")
  public CompanyModel update(@RequestBody @Valid CompanyModel model, @PathVariable long id) {
    final Company company = companyService.getCompanyById(id);
    final Company updatedCompany = model.updateEntity(company);
    return modelAssembler.toModel(companyService.save(updatedCompany));
  }
}
