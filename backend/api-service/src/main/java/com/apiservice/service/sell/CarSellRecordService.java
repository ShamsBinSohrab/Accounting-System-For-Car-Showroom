package com.apiservice.service.sell;

import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.entity.tenant.sell.SellRecord;
import com.apiservice.repository.car.CarRepository;
import com.apiservice.repository.sell.CarSellRecordRepository;
import com.apiservice.utils.CreateUpdateAuditor;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarSellRecordService {

  private final CarSellRecordRepository carSellRecordRepository;
  private final CreateUpdateAuditor auditor;

  @Transactional(readOnly = true)
  public List<CarSellRecord> getAll() {
    return carSellRecordRepository.findAll();
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
