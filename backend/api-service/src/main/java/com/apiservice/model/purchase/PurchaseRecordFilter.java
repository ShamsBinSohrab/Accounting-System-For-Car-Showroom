package com.apiservice.model.purchase;

import com.apiservice.enums.purchase.PurchaseType;
import java.time.ZonedDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class PurchaseRecordFilter {

  private String chassisNo;
  private PurchaseType purchaseType;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private ZonedDateTime purchaseDateFrom;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private ZonedDateTime purchaseDateTo;
}
