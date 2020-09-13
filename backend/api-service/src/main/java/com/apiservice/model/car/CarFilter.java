package com.apiservice.model.car;

import com.apiservice.enums.car.CarFuelType;
import com.apiservice.enums.car.CarMake;
import com.apiservice.enums.car.CarTransmission;
import com.apiservice.enums.car.CarType;
import lombok.Data;

@Data
public class CarFilter {

  private String chassisNo;
  private boolean draft;
  private CarMake make;
  private String name;
  private CarType type;
  private String modelYear;
  private String color;
  private String engineNo;
  private Integer mileage;
  private Integer cc;
  private CarTransmission transmission;
  private CarFuelType fuelType;
}
