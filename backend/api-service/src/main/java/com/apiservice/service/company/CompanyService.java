package com.apiservice.service.company;

import com.apiservice.authentication.JwtTokenUtil;
import com.apiservice.entity.master.company.Company;
import com.apiservice.repository.company.CompanyRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final JwtTokenUtil jwtTokenUtil;
  private final RestTemplate restTemplate;

  @Value("${migration.internal.api.url}")
  private String migrationInternalApiUrl;

  @Transactional(readOnly = true)
  public String getCompanyToken(long id) {
    final Company company = companyRepository.findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(Company.class, id));
    return jwtTokenUtil.generateToken(company.getUuid().toString());
  }

  @Transactional
  public void create(Company company) {
    Mono<ResponseEntity<String>> result =
        WebClient.create()
            .post().uri(URI.create(migrationInternalApiUrl))
            .contentType(MediaType.APPLICATION_JSON).
            body(Mono.just(company), Company.class)
        .retrieve()
        .toEntity(String.class);
    result.subscribe(
        response -> {
          companyRepository.save(company);
          log.info("Created database schema for company {}", company.getCompanyName());
        },
        error -> log.error(error.getMessage(), error)
    );

  }

}
