package com.apiservice.model.car;

import com.apiservice.enums.car.CarMake;
import com.apiservice.enums.car.CarType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Digits;
import lombok.Data;

@Data
public class CarDetailsModel {

  private CarMake make;
  private String name;
  private String color;

  @Enumerated(value = EnumType.STRING)
  private CarType type;

  @Digits(integer = 4, fraction = 0)
  private int modelYear;
}
