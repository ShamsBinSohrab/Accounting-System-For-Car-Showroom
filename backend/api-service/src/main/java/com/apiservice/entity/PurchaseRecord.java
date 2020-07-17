package com.apiservice.entity;

import com.apiservice.enums.PurchaseType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PurchaseRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "car_id", nullable = false)
  private int carId;

  @Enumerated(EnumType.STRING)
  @Column(name = "purchase_type", nullable = false)
  private PurchaseType purchaseType;

  @Column(name = "base_price", nullable = false)
  private double basePrice;

  @Column(name = "lc_charge")
  private double lcCharge;

  @Column(name = "shipping_charge")
  private double shippingCharge;

  @Column(name = "tax")
  private double tax;

  @Column(name = "ait")
  private double advancedIncomeTax;

  @Column(name = "cnf_charge")
  private double cnfCharge;

  @Column(name = "transportation_charge")
  private double transportationCharge;

  @Column(name = "garage_charge")
  private double garageCharge;

  @Column(name = "miscellaneous_charge")
  private double miscellaneousCharge;

  @Column(name = "deleted", nullable = false)
  private boolean deleted;
}
