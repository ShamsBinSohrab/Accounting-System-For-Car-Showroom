package com.apiservice.entity.tenant.sell;

import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
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
@Table(name = "car_sell_record")
public class CarSellRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @JoinColumn(name = "car_purchase_record_id", unique = true)
  @OneToOne(fetch = FetchType.EAGER)
  private CarPurchaseRecord purchaseRecord;

  @JoinColumn(name = "sell_record_id", unique = true)
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private SellRecord sellRecord;
}
