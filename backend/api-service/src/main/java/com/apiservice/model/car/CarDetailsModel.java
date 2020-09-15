package com.apiservice.model.car;

import com.apiservice.enums.car.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

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
