package com.apiservice.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "car")
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "make", nullable = false)
  private String make;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "type", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private CarType type;

  @Digits(integer = 4, fraction = 0)
  @Column(name = "model_year", nullable = false)
  private int modelYear;

  @Column(name = "color", nullable = false)
  private String color;

  @Column(name = "chassis_no", nullable = false, unique = true)
  private String chassisNo;

  @JsonIgnore
  @Column(name = "deleted", nullable = false)
  private boolean deleted;
}