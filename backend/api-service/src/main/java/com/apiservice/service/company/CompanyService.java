package com.apiservice.service.company;

import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.entity.master.company.Company;
import com.apiservice.repository.company.CompanyRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final JwtTokenUtil jwtTokenUtil;

  @Transactional(readOnly = true)
  public String getCompanyToken(long id) {
    final Company company = companyRepository.findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(Company.class, id));
    return jwtTokenUtil.generateToken(company.getUuid().toString());
  }

  @Transactional
  public void save(Company company) {
    companyRepository.save(company);
  }

}
