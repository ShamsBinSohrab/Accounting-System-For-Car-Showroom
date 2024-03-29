package com.apiservice.entity.tenant.purchase;

import com.apiservice.entity.tenant.Auditable;
import com.apiservice.entity.tenant.car.Car;
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
public class CarPurchaseRecord extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @JoinColumn(name = "car_id", unique = true)
  @OneToOne(fetch = FetchType.EAGER)
  private Car car;

  @JoinColumn(name = "purchase_record_id", unique = true)
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private PurchaseRecord purchaseRecord;
}
