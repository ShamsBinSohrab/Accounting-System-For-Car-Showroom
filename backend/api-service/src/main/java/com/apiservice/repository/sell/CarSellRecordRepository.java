package com.apiservice.repository.sell;

import com.apiservice.entity.tenant.sell.CarSellRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarSellRecordRepository extends JpaRepository<CarSellRecord, Long> {

  Optional<CarSellRecord> findByPurchaseRecordId(long purchaseRecordId);

  boolean existsByPurchaseRecordId(long purchaseRecordId);
}
