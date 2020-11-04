package com.apiservice.repository.sell;

import com.apiservice.entity.tenant.sell.CarSellRecord;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarSellRecordRepository extends JpaRepository<CarSellRecord, Long>,
        JpaSpecificationExecutor<CarSellRecord> {

  Optional<CarSellRecord> findByPurchaseRecordId(long purchaseRecordId);

  boolean existsByPurchaseRecordId(long purchaseRecordId);
}
