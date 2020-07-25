package com.apiservice.model.purchase;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PurchaseRecordDetailsModel {

  private BigDecimal basePrice;
  private BigDecimal lcCharge;
  private BigDecimal shippingCharge;
  private BigDecimal tax;
  private BigDecimal cnfCharge;
  private BigDecimal transportationCharge;
  private BigDecimal garageCharge;
  private BigDecimal miscellaneousCharge;
}
