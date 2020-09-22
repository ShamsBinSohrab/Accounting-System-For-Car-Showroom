package com.apiservice.service.operator;

import com.apiservice.entity.master.company.Company;
import com.apiservice.entity.master.operator.Operator;
import com.apiservice.multitenancy.TenantContext;
import com.apiservice.repository.company.CompanyRepository;
import com.apiservice.repository.operator.OperatorRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperatorService {

  private final OperatorRepository operatorRepository;
  private final PasswordEncoder passwordEncoder;
  private final CompanyRepository companyRepository;

  @Transactional(readOnly = true)
  public Operator getByUsername(String username) {
    return operatorRepository
        .findByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));
  }

  @Transactional(readOnly = true)
  public List<Operator> getAllOperators() {
    final UUID companyUuid = getCurrentCompanyUuid();
    return operatorRepository.findAllOperatorsByCompanyUuid(companyUuid);
  }

  @Transactional(readOnly = true)
  public Operator getOperatorById(long id) {
    final UUID companyUuid = getCurrentCompanyUuid();
    return operatorRepository
        .findByIdAndCompanyUuid(id, companyUuid)
        .orElseThrow(() -> EntityNotFoundException.of(Operator.class, id));
  }

  @Transactional
  public void delete(long id) {
    final UUID companyUuid = getCurrentCompanyUuid();
    final Operator operator =
        operatorRepository
            .findByIdAndCompanyUuid(id, companyUuid)
            .orElseThrow(() -> EntityNotFoundException.of(Operator.class, id));
    operatorRepository.delete(operator);
  }

  @Transactional
  public void save(Operator operator) {
    operatorRepository.save(operator);
  }

  @Transactional
  public void createNewOperator(Operator operator) {
    final UUID companyUuid = getCurrentCompanyUuid();
    final Company company =
        companyRepository.findByUuid(companyUuid).orElseThrow();
    operator.setCompany(company);
    encodePassword(operator);
    save(operator);
  }

  @Transactional
  public void changePassword(Operator operator) {
    encodePassword(operator);
    save(operator);
  }

  @Transactional
  public Operator getByEmail(String email) {
    return operatorRepository.findByEmail(email).orElseThrow();
  }

  private void encodePassword(Operator operator) {
    final String encodedPassword = passwordEncoder.encode(operator.getPassword());
    operator.setPassword(encodedPassword);
  }

  private UUID getCurrentCompanyUuid() {
    return Optional.ofNullable(TenantContext.getCurrentTenant())
        .map(UUID::fromString).orElseThrow();
  }

}
