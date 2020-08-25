package com.apiservice.service.purchase;

import com.apiservice.entity.car.Car;
import com.apiservice.entity.purchase.CarPurchaseRecord;
import com.apiservice.entity.purchase.PurchaseRecord;
import com.apiservice.repository.car.CarRepository;
import com.apiservice.repository.purchase.CarPurchaseRecordRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarPurchaseRecordService {

  private final CarPurchaseRecordRepository carPurchaseRecordRepository;
  private final CarRepository carRepository;

  @Transactional(readOnly = true)
  public List<CarPurchaseRecord> getAll() {
    return carPurchaseRecordRepository.findAll();
  }

  @Transactional(readOnly = true)
  public CarPurchaseRecord getById(long id) {
    return carPurchaseRecordRepository.findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(PurchaseRecord.class, id));
  }

  @Transactional
  public void save(CarPurchaseRecord carPurchaseRecord, String chassisNo) {
    Car car = carRepository.findByChassisNo(chassisNo)
        .orElseThrow();
    carPurchaseRecord.setCar(car);
    carPurchaseRecordRepository.save(carPurchaseRecord);
  }

  @Transactional
  public void delete(long id) {
    carPurchaseRecordRepository.deleteById(id);
  }
}
