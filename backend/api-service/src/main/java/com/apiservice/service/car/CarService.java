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
    return carRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Car getCarById(long id) {
    return carRepository.findById(id)
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
    Car car = carRepository.findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(Car.class, id));
    carRepository.delete(car);
  }

  @Transactional
  public Car getByChassisNoOrNew(String chassisNo) {
    return carRepository.findByChassisNo(chassisNo)
        .orElseGet(Car::newDraftCar);
  }
}
