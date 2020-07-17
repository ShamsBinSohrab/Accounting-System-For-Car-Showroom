package com.apiservice.service;

import com.apiservice.entity.Car;
import com.apiservice.repository.CarRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

  private final CarRepository carRepository;
  private final ModelMapper modelMapper;

  @PostConstruct
  private void configModelMapper() {
    modelMapper.createTypeMap(Car.class, Car.class)
        .addMappings(mapper -> mapper.skip(Car::setId));
  }

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
   * Update a car.
   */
  @Transactional
  public Car update(long id, Car car) {
    Car carToUpdate = carRepository.findByIdAndDeletedIsFalse(id)
        .orElseThrow(() -> EntityNotFoundException.of(Car.class, id));
    modelMapper.map(car, carToUpdate);
    return carRepository.save(carToUpdate);
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
}
