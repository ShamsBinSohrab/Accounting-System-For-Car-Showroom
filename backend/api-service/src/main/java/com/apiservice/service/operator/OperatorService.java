package com.apiservice.service.operator;

import com.apiservice.entity.car.Car;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import com.apiservice.entity.operator.Operator;
import com.apiservice.repository.operator.OperatorRepository;
import java.util.List;
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

  @Transactional(readOnly = true)
  public List<Operator> getAllOperators() {
    return operatorRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Operator getOperatorById(long id) {
    return operatorRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.of(Operator.class, id));
  }

  @Transactional
  public void delete(long id) {
    Operator operator = operatorRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.of(Operator.class, id));
    operatorRepository.delete(operator);
  }

  @Transactional
  public Operator save(Operator user) {
    return operatorRepository.save(user);
  }

}
