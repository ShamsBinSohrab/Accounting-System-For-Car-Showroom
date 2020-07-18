package com.apiservice.entity;

import com.apiservice.enums.PurchaseType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
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

  @Digits(integer = 10, fraction = 2)
  @Column(name = "base_price", nullable = false)
  private BigDecimal basePrice;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "lc_charge")
  private BigDecimal lcCharge;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "shipping_charge")
  private BigDecimal shippingCharge;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "tax")
  private BigDecimal tax;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "ait")
  private BigDecimal advancedIncomeTax;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "cnf_charge")
  private BigDecimal cnfCharge;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "transportation_charge")
  private BigDecimal transportationCharge;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "garage_charge")
  private BigDecimal garageCharge;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "miscellaneous_charge")
  private BigDecimal miscellaneousCharge;

  @Column(name = "deleted", nullable = false)
  private boolean deleted;
}
