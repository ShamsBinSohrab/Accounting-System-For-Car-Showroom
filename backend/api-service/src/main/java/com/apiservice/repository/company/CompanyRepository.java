package com.apiservice.repository.company;

import com.apiservice.entity.master.company.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

  Company findByUuid(UUID uuid);
}
