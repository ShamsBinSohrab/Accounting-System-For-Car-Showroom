package com.apiservice.entity.tenant.car;

import com.apiservice.enums.car.CarMake;
import com.apiservice.enums.car.CarType;
import com.apiservice.enums.car.FuelType;
import com.apiservice.enums.car.Transmission;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Data
@Entity
@Table(name = "car_details")
@TypeDefs({@TypeDef(name = "string-array", typeClass = StringArrayType.class)})
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

  @Column(name = "options", nullable = false)
  @Type(type = "string-array")
  private String[] options;

  @Column(name = "engine_no")
  private String engineNo;

  @Column(name = "mileage")
  private Integer mileage;

  @Column(name = "cc", nullable = false)
  private Integer cc;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "transmission", nullable = false)
  private Transmission transmission;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "fuel_type", nullable = false)
  private FuelType fuelType;
}
