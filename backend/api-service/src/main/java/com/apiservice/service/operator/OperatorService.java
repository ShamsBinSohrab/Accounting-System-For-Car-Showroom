package com.apiservice.service.operator;

import com.apiservice.entity.operator.Operator;
import com.apiservice.model.operator.ChangePasswordModel;
import com.apiservice.repository.operator.OperatorRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.List;
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

  @Transactional(readOnly = true)
  public Operator getByUsername(String username) {
    return operatorRepository
        .findByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));
  }

  @Transactional(readOnly = true)
  public List<Operator> getAllOperators() {
    return operatorRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Operator getOperatorById(long id) {
    return operatorRepository
        .findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(Operator.class, id));
  }

  @Transactional
  public void delete(long id) {
    Operator operator =
        operatorRepository
            .findById(id)
            .orElseThrow(() -> EntityNotFoundException.of(Operator.class, id));
    operatorRepository.delete(operator);
  }

  @Transactional
  public void save(Operator operator) {
    final String encodedPassword = passwordEncoder.encode(operator.getPassword());
    operator.setPassword(encodedPassword);
    operatorRepository.save(operator);
  }

  @Transactional
  public void update(Operator operator) {
    operatorRepository.save(operator);
  }

  @Transactional(readOnly = true)
  public boolean isValidUser(String username) {
    return operatorRepository.findByUsername(username).isPresent();
  }
}
