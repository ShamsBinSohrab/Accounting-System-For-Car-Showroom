package com.apiservice.repository;

import com.apiservice.entity.PurchaseRecord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Long> {

  List<PurchaseRecord> findAllByDeletedIsFalse();

  Optional<PurchaseRecord> findByIdAndDeletedIsFalse(long id);

  @Modifying
  @Query("UPDATE PurchaseRecord SET deleted = true where id = :id and deleted = false")
  int deleteById(long id);
}
