package com.apiservice.service.car;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.model.car.CarFilter;
import com.apiservice.model.car.CarQueryBuilder;
import com.apiservice.repository.car.CarRepository;
import com.apiservice.repository.purchase.CarPurchaseRecordRepository;
import com.apiservice.repository.sell.CarSellRecordRepository;
import com.apiservice.utils.CreateUpdateAuditor;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import com.apiservice.utils.pagination.PaginationService;
import com.apiservice.utils.pagination.QueryBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

  private final CarRepository carRepository;
  private final CarPurchaseRecordRepository purchaseRecordRepository;
  private final CarSellRecordRepository sellRecordRepository;
  private final PaginationService<Car> paginationService;
  private final CreateUpdateAuditor auditor;

  @Transactional(readOnly = true)
  public Page<Car> getAllWithPaginationAndFilter(CarFilter filter, Pageable pageable) {
    final QueryBuilder<Car> queryBuilder = new CarQueryBuilder(filter);
    return paginationService.paginate(carRepository, queryBuilder, pageable);
  }

  @Transactional(readOnly = true)
  public List<Car> getAll() {
    return carRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Car getCarById(long id) {
    return carRepository.findById(id).orElseThrow(() -> EntityNotFoundException.of(Car.class, id));
  }

  @Transactional
  public Car save(Car car) {
    auditor.auditCreateUpdate(car);
    return carRepository.save(car);
  }

  /** Set deleted flag to a car. */
  @Transactional
  public void delete(long id) {
    final Car car =
        carRepository.findById(id).orElseThrow(() -> EntityNotFoundException.of(Car.class, id));
    // Deleting any purchase record and sell record if exists
    purchaseRecordRepository
        .findByCarId(car.getId())
        .ifPresent(
            pr -> {
              sellRecordRepository
                  .findByPurchaseRecordId(pr.getId())
                  .ifPresent(sellRecordRepository::delete);
              purchaseRecordRepository.delete(pr);
            });
    carRepository.delete(car);
  }
}
