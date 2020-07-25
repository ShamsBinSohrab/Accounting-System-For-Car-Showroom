package com.apiservice.service.car;

import com.apiservice.entity.car.Car;
import com.apiservice.repository.car.CarRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

  private final CarRepository carRepository;

  @Transactional(readOnly = true)
  public List<Car> getAllCars() {
    return carRepository.findAllByDeletedIsFalse();
  }

  @Transactional(readOnly = true)
  public Car getCarById(long id) {
    return carRepository.findByIdAndDeletedIsFalse(id)
        .orElseThrow(() -> EntityNotFoundException.of(Car.class, id));
  }

  @Transactional
  public Car save(Car car) {
    return carRepository.save(car);
  }

  /**
   * Set deleted flag to a car.
   */
  @Transactional
  public void delete(long id) {
    if (carRepository.deleteById(id) == 0) {
      throw EntityNotFoundException.of(Car.class, id);
    }
  }

  @Transactional
  public Car getByChassisNo(String chassisNo) {
    return carRepository.findByChassisNo(chassisNo).orElseThrow();
  }
}
