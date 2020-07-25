package com.apiservice.entity.car;

import com.apiservice.enums.CarMake;
import com.apiservice.enums.CarType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

  @OneToOne(fetch = FetchType.LAZY)
  private Car car;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "make", nullable = false)
  private CarMake make;

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "type", nullable = false)
  private CarType type;

  @Digits(integer = 4, fraction = 0)
  @Column(name = "model_year", nullable = false)
  private int modelYear;

  @Column(name = "color", nullable = false)
  private String color;
}
