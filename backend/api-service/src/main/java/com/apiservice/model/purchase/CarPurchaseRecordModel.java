package com.apiservice.model.purchase;

import com.apiservice.entity.purchase.CarPurchaseRecord;
import com.apiservice.enums.purchase.PurchaseType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarPurchaseRecordModel {

  private static final ModelMapper mapper = new ModelMapper();

  static {
    mapper.createTypeMap(CarPurchaseRecordModel.class, CarPurchaseRecord.class)
        .addMappings(m -> m.skip(CarPurchaseRecord::setCar));
  }

  private Long id;

  @JsonProperty("chassisNo")
  private String carChassisNo;

  @Enumerated(EnumType.STRING)
  private PurchaseType purchaseType;

  private ZonedDateTime purchaseDate;

  @JsonProperty("basePrice")
  private BigDecimal purchaseRecordBasePrice;

  @JsonProperty("lcCharge")
  private BigDecimal purchaseRecordLcCharge;

  @JsonProperty("shippingCharge")
  private BigDecimal purchaseRecordShippingCharge;

  @JsonProperty("tax")
  private BigDecimal purchaseRecordTax;

  @JsonProperty("cnfCharge")
  private BigDecimal purchaseRecordCnfCharge;

  @JsonProperty("transportationCharge")
  private BigDecimal purchaseRecordTransportationCharge;

  @JsonProperty("garageCharge")
  private BigDecimal purchaseRecordGarageCharge;

  @JsonProperty("miscellaneousCharge")
  private BigDecimal purchaseRecordMiscellaneousCharge;


  public static CarPurchaseRecordModel toModel(CarPurchaseRecord carPurchaseRecord) {
    return mapper.map(carPurchaseRecord, CarPurchaseRecordModel.class);
  }

  public CarPurchaseRecord toEntity() {
    return mapper.map(this, CarPurchaseRecord.class);
  }
}
