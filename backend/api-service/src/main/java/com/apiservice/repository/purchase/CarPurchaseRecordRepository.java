package com.apiservice.repository.purchase;

import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarPurchaseRecordRepository extends JpaRepository<CarPurchaseRecord, Long>,
    JpaSpecificationExecutor<CarPurchaseRecord> {

  Optional<CarPurchaseRecord> findByCarId(long carId);
}
