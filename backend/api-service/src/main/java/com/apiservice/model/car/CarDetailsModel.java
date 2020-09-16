package com.apiservice.model.car;

import com.apiservice.enums.car.CarMake;
import com.apiservice.enums.car.CarOption;
import com.apiservice.enums.car.CarType;
import com.apiservice.enums.car.FuelType;
import com.apiservice.enums.car.Transmission;
import java.util.Collections;
import java.util.Set;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;

@Data
public class CarDetailsModel {

  private CarMake make;
  private String name;
  private String color;

  @Enumerated(value = EnumType.STRING)
  private CarType type;

  private String modelYear;

  private Set<CarOption> options = Collections.emptySet();

  private String engineNo;
  private int mileage;
  private int cc;

  @Enumerated(value = EnumType.STRING)
  private Transmission transmission;

  @Enumerated(value = EnumType.STRING)
  private FuelType fuelType;
}
