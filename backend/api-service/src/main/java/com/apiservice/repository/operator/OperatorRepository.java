package com.apiservice.repository.operator;

import com.apiservice.entity.master.operator.Operator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

  Optional<Operator> findByUsername(String username);

  List<Operator> findAllOperatorsByCompanyUuid(UUID uuid);

  Optional<Operator> findByIdAndCompanyUuid(long id, UUID uuid);
}
