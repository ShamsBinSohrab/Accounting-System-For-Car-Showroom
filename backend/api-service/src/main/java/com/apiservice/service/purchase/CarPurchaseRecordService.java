package com.apiservice.service.purchase;

import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.entity.tenant.purchase.PurchaseRecord;
import com.apiservice.model.purchase.PurchaseRecordCriteria;
import com.apiservice.model.purchase.PurchaseRecordQueryBuilder;
import com.apiservice.repository.purchase.CarPurchaseRecordRepository;
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

  @Transactional(readOnly = true)
  public Page<CarPurchaseRecord> getAllWithPaginationAndFilter(
      PurchaseRecordCriteria criteria, Pageable pageable) {
    final QueryBuilder<CarPurchaseRecord> queryBuilder = new PurchaseRecordQueryBuilder(criteria);
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
    carPurchaseRecordRepository.save(carPurchaseRecord);
  }

  @Transactional
  public void delete(long id) {
    carPurchaseRecordRepository.deleteById(id);
  }
}
