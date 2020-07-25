package com.apiservice.entity.purchase;

import com.apiservice.entity.car.Car;
import com.apiservice.enums.purchase.PurchaseType;
import java.time.ZonedDateTime;
import javax.persistence.CascadeType;
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
import lombok.Data;

@Data
@Entity
public class PurchaseRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @JoinColumn(name = "car_id")
  @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
  private Car car;

  @Enumerated(EnumType.STRING)
  @Column(name = "purchase_type", nullable = false)
  private PurchaseType purchaseType;

  @JoinColumn(name = "purchase_record_details_id")
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private PurchaseRecordDetails details;

  @Column(name = "purchase_date", nullable = false)
  private ZonedDateTime purchaseDate;
}
