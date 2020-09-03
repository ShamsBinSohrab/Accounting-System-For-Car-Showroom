package com.apiservice.service.car;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.model.car.CarFilter;
import com.apiservice.model.car.CarModel;
import com.apiservice.model.car.CarQueryBuilder;
import com.apiservice.repository.car.CarRepository;
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
public class CarService {

  private final CarRepository carRepository;
  private final CarPurchaseRecordRepository carPurchaseRecordRepository;
  private final PaginationService<Car> paginationService;

  @Transactional(readOnly = true)
  public Page<Car> getAllWithPaginationAndFilter(
      CarFilter filter, Pageable pageable) {
    final QueryBuilder<Car> queryBuilder = new CarQueryBuilder(filter);
    return paginationService.paginate(carRepository, queryBuilder, pageable);
  }

  @Transactional(readOnly = true)
  public Car getCarById(long id) {
    return carRepository.findById(id).orElseThrow(() -> EntityNotFoundException.of(Car.class, id));
  }

  @Transactional
  public Car save(Car car) {
    return carRepository.save(car);
  }

  /** Set deleted flag to a car. */
  @Transactional
  public void delete(long id) {
    Car car =
        carRepository.findById(id).orElseThrow(() -> EntityNotFoundException.of(Car.class, id));

    // Deleting any purchase record if exists
    carPurchaseRecordRepository
        .findByCarId(car.getId())
        .ifPresent(carPurchaseRecordRepository::delete);
    carRepository.delete(car);
  }

  @Transactional
  public Car getByChassisNoAndDraft(String chassisNo, boolean draft) {
    return draft
        ? carRepository.findByChassisNo(chassisNo).orElse(CarModel.newDraftCar(chassisNo))
        : carRepository
            .findByChassisNo(chassisNo)
            .orElseThrow(
                () ->
                    EntityNotFoundException.of(
                        Car.class, "No Car found with chassis no: " + chassisNo));
  }
  @Transactional(readOnly = true)
  public Car getCarByChassisNo(String chassisNo) {
    return carRepository.findByChassisNo(chassisNo).orElseThrow(() -> EntityNotFoundException.of(Car.class, "No Car found with chassis no: " + chassisNo));
  }
}
