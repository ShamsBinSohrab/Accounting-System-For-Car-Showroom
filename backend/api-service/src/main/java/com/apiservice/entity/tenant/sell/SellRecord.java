package com.apiservice.entity.tenant.sell;

import com.apiservice.enums.sell.SellType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import lombok.Data;

@Data
@Entity
@Table(name = "sell_record")
public class SellRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "sell_type", nullable = false)
  private SellType sellType;

  @Column(name = "sell_date", nullable = false)
  private ZonedDateTime sellDate;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "paid_as_advance", nullable = false)
  private BigDecimal paidAsAdvance;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "paid_on_delivery", nullable = false)
  private BigDecimal paidOnDelivery;

  @Digits(integer = 10, fraction = 2)
  @Column(name = "paid_after_delivery", nullable = false)
  private BigDecimal paidAfterDelivery;

  @Column(name = "purchase_letter_no")
  private String purchaseLetterNo;

}
