package com.apiservice.model.car;

import com.apiservice.entity.tenant.car.Car;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CarModel extends RepresentationModel<CarModel> {

  private static final ModelMapper mapper = new ModelMapper();

  private long id;
  private String chassisNo;
  private boolean draft;
  private CarDetailsModel details;

  public Car toEntity() {
    return mapper.map(this, Car.class);
  }

  public void updateEntity(Car car) {
    final long id = car.getId();
    mapper.map(this, car);
    car.setId(id);
  }
}
