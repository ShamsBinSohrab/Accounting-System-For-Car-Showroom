package com.apiservice.entity.tenant.car;

import com.apiservice.enums.car.CarMake;
import com.apiservice.enums.car.CarType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import lombok.Data;

@Data
@Entity
@Table(name = "car_details")
public class CarDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "make", nullable = false)
  private CarMake make;

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "type", nullable = false)
  private CarType type;

  @Column(name = "model_year", nullable = false)
  private String modelYear;

  @Column(name = "color", nullable = false)
  private String color;
}
