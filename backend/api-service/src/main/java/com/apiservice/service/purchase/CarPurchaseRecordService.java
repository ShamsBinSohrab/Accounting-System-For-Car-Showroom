package com.apiservice.service.purchase;

import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.entity.tenant.purchase.PurchaseRecord;
import com.apiservice.model.purchase.PurchaseRecordFilter;
import com.apiservice.model.purchase.PurchaseRecordQueryBuilder;
import com.apiservice.repository.purchase.CarPurchaseRecordRepository;
import com.apiservice.utils.CreateUpdateAuditor;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import com.apiservice.utils.pagination.PaginationService;
import com.apiservice.utils.pagination.QueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarPurchaseRecordService {

  private final CarPurchaseRecordRepository carPurchaseRecordRepository;
  private final PaginationService<CarPurchaseRecord> paginationService;
  private final CreateUpdateAuditor auditor;

  @Transactional(readOnly = true)
  public Page<CarPurchaseRecord> getAllWithPaginationAndFilter(
      PurchaseRecordFilter filter, Pageable pageable) {
    final QueryBuilder<CarPurchaseRecord> queryBuilder = new PurchaseRecordQueryBuilder(filter);
    return paginationService.paginate(carPurchaseRecordRepository, queryBuilder, pageable);
  }

  @Transactional(readOnly = true)
  public CarPurchaseRecord getById(long id) {
    return carPurchaseRecordRepository
        .findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(PurchaseRecord.class, id));
  }

  @Transactional
  public void save(CarPurchaseRecord carPurchaseRecord) {
    auditor.auditCreateUpdate(carPurchaseRecord);
    carPurchaseRecordRepository.save(carPurchaseRecord);
  }

  @Transactional
  public void delete(long id) {
    carPurchaseRecordRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public CarPurchaseRecord getByCarId(long carId) {
    return carPurchaseRecordRepository.findByCarId(carId)
        .orElseThrow();
  }
}
