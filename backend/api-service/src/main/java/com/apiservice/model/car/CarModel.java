package com.apiservice.model.car;

import com.apiservice.entity.car.Car;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarModel {

  private static final ModelMapper mapper = new ModelMapper();

  static {
    mapper.createTypeMap(CarModel.class, Car.class, "UpdateEntity")
        .addMappings(mapper -> mapper.skip(Car::setId));
  }

  private long id;
  private String chassisNo;
  private boolean draft;
  private CarDetailsModel details;

  public Car toEntity() {
    return mapper.map(this, Car.class);
  }

  public static CarModel toModel(Car car) {
    return mapper.map(car, CarModel.class);
  }

  public void updateEntity(Car car) {
    mapper.map(this, car, "UpdateEntity");
  }
}
