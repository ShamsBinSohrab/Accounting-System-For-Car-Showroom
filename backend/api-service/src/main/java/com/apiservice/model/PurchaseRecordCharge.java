package com.apiservice.model;

import com.apiservice.enums.ChargeType;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PurchaseRecordCharge {

  @NotEmpty
  private ChargeType chargeType;

  @Digits(integer = 10, fraction = 2)
  private BigDecimal amount;

}
