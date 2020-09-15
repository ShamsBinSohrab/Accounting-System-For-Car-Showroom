package com.apiservice.model.car;

import com.apiservice.enums.car.FuelType;
import com.apiservice.enums.car.CarMake;
import com.apiservice.enums.car.Transmission;
import com.apiservice.enums.car.CarType;
import lombok.Data;

@Data
public class CarFilter {

  private String chassisNo;
  private Boolean draft;
  private CarMake make;
  private String name;
  private CarType type;
  private String modelYear;
  private String color;
  private String engineNo;
  private Integer mileage;
  private Integer cc;
  private Transmission transmission;
  private FuelType fuelType;
}
