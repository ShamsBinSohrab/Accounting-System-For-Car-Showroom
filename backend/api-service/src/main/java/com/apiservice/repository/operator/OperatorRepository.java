package com.apiservice.repository.operator;

import com.apiservice.entity.master.operator.Operator;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

  Optional<Operator> findByUsername(String username);
}
