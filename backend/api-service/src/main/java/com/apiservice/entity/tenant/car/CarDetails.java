package com.apiservice.entity.tenant.car;

import com.apiservice.enums.car.*;

import javax.persistence.*;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
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
  private int mileage;

  @Column(name = "cc", nullable = false)
  private int cc;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "transmission", nullable = false)
  private Transmission transmission;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "fuel_type", nullable = false)
  private FuelType fuelType;
}
