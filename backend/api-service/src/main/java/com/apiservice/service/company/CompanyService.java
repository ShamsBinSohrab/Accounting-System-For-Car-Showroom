package com.apiservice.service.company;

import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.entity.master.company.Company;
import com.apiservice.model.company.CompanyCreationException;
import com.apiservice.model.company.CompanyFilter;
import com.apiservice.model.company.CompanyQueryBuilder;
import com.apiservice.repository.company.CompanyRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import com.apiservice.utils.pagination.PaginationService;
import com.apiservice.utils.pagination.QueryBuilder;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final JwtTokenUtil jwtTokenUtil;
  private final RestTemplate restTemplate;
  private final ThreadPoolTaskExecutor taskExecutor;
  private final PaginationService<Company> paginationService;

  @Value("${migration.internal.api.url}")
  private String migrationInternalApiUrl;

  @Transactional(readOnly = true)
  public String getCompanyToken(long id) {
    final Company company =
        companyRepository
            .findById(id)
            .orElseThrow(() -> EntityNotFoundException.of(Company.class, id));
    return jwtTokenUtil.generateToken(company.getUuid());
  }

  @Transactional
  public Company create(Company company) {
    try {
      ListenableFuture<Company> listenableFuture =
          taskExecutor.submitListenable(
              () -> {
                final URI uri = URI.create(migrationInternalApiUrl);
                final RequestEntity<Company> request =
                    RequestEntity.post(uri).body(company);
                final ResponseEntity<String> response =
                    restTemplate.exchange(request, String.class);
                log.info("Created schema: {}", response.getBody());
                companyRepository.save(company);
                return company;
              });
      return listenableFuture.get(20, TimeUnit.SECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException ex) {
      if (ex.getClass().equals(InterruptedException.class)) {
        Thread.currentThread().interrupt();
      }
      log.error("Unable to create company", ex);
      throw new CompanyCreationException();
    }
  }

  @Transactional(readOnly = true)
  public Company getByUuid(UUID uuid) {
    return companyRepository.findByUuid(uuid).orElseThrow(
        () -> EntityNotFoundException.of(Company.class, "No company found with uuid: " + uuid));
  }

  @Transactional(readOnly = true)
  public Company getCompanyById(long id) {
    return companyRepository.findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(Company.class, id));
  }

  @Transactional
  public void save(Company company) {
    companyRepository.save(company);
  }

  @Transactional(readOnly = true)
  public Page<Company> getAllWithPaginationAndFilter(
      CompanyFilter filter, Pageable pageable) {
    final QueryBuilder<Company> queryBuilder = new CompanyQueryBuilder(filter);
    return paginationService.paginate(companyRepository, queryBuilder, pageable);
  }
}
