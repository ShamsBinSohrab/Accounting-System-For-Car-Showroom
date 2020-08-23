package com.apiservice.service.operator;

import com.apiservice.entity.operator.Operator;
import com.apiservice.repository.operator.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperatorService {

  private final OperatorRepository operatorRepository;

  @Transactional(readOnly = true)
  public Operator getByUsername(String username) {
    return operatorRepository
        .findByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));
  }

  @Transactional
  public Operator save(Operator user) {
    return operatorRepository.save(user);
  }
}
