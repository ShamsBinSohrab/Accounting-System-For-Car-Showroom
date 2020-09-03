package com.apiservice.model.car;

import com.apiservice.enums.car.CarMake;
import com.apiservice.enums.car.CarType;
import lombok.Data;

@Data
public class CarFilter {

  private String chassisNo;
  private boolean draft;
  private CarMake make;
  private String name;
  private CarType type;
  private int modelYear;
  private String color;
}
