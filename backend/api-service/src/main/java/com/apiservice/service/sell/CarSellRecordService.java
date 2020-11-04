package com.apiservice.service.sell;

import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.entity.tenant.sell.SellRecord;
import com.apiservice.model.sell.CarSellRecordFilter;
import com.apiservice.model.sell.CarSellRecordQueryBuilder;
import com.apiservice.repository.sell.CarSellRecordRepository;
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
public class CarSellRecordService {

  private final CarSellRecordRepository carSellRecordRepository;
  private final CreateUpdateAuditor auditor;
  private final PaginationService<CarSellRecord> paginationService;

  @Transactional(readOnly = true)
  public Page<CarSellRecord> getAllWithPaginationAndFilter(
          CarSellRecordFilter filter, Pageable pageable) {
    final QueryBuilder<CarSellRecord> queryBuilder = new CarSellRecordQueryBuilder(filter);
    return paginationService.paginate(carSellRecordRepository, queryBuilder, pageable);
  }

  @Transactional(readOnly = true)
  public CarSellRecord getById(long id) {
    return carSellRecordRepository.findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(SellRecord.class, id));
  }

  @Transactional
  public void save(CarSellRecord carSellRecord) {
    auditor.auditCreateUpdate(carSellRecord);
    carSellRecordRepository.save(carSellRecord);
  }

  @Transactional
  public void delete(long id) {
    carSellRecordRepository.deleteById(id);
  }
}
