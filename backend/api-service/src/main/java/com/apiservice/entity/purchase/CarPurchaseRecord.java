package com.apiservice.entity.purchase;

import com.apiservice.entity.car.Car;
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
@Table(name = "car_purchase_record")
public class CarPurchaseRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @JoinColumn(name = "car_id", unique = true)
  @OneToOne(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Car car;

  @JoinColumn(name = "purchase_record_id", unique = true)
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private PurchaseRecord purchaseRecord;
}
