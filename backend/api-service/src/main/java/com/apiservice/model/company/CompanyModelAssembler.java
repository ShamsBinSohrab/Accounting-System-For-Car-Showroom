package com.apiservice.model.company;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.authentication.AuthenticationService;
import com.apiservice.authentication.Scopes;
import com.apiservice.controller.company.CompanyController;
import com.apiservice.entity.master.company.Company;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyModelAssembler implements RepresentationModelAssembler<Company, CompanyModel> {

  private final ModelMapper mapper;
  private final HttpServletRequest request;
  private final AuthenticationService authService;

  @Override
  public CompanyModel toModel(Company company) {
    final CompanyModel model = mapper.map(company, CompanyModel.class);
    final List<Scopes> scopes = authService.extractScopesFromToken(request);
    if(scopes.contains("COMPANY_READ"))
      addLinkToDetails(model);
    if(scopes.contains("COMPANY_WRITE"))
    {
      addLinkToCreate(model);
      addLinkToUpdate(model);
    }
    if (model.isActive()) {
      addLinkToCompanyToken(model);
    }
    return model;
  }

  private void addLinkToDetails(CompanyModel model) {
    model.add(linkTo(methodOn(CompanyController.class).details(model.getId())).withRel("details"));
  }

  private void addLinkToCompanyToken(CompanyModel model) {
    model.add(
        linkTo(methodOn(CompanyController.class).getCompanyToken(model.getId())).withRel("token"));
  }

  private void addLinkToCreate(CompanyModel model) {
    model.add(
        linkTo(methodOn(CompanyController.class).create(model)).withRel("create"));
  }

  private void addLinkToUpdate(CompanyModel model) {
    model.add(
        linkTo(methodOn(CompanyController.class).update(model, model.getId())).withRel("update"));
  }
}
