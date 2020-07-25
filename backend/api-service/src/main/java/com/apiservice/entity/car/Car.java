package com.apiservice.entity.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "car")
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "chassis_no", nullable = false, unique = true)
  private String chassisNo;

  @Column(name = "draft", nullable = false)
  private boolean draft;

  @JsonIgnore
  @Column(name = "deleted", nullable = false)
  private boolean deleted;

  @JoinColumn(name = "car_details_id")
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private CarDetails details;
}