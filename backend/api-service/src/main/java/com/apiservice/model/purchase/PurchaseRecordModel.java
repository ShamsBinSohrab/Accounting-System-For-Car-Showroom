package com.apiservice.model.purchase;

import com.apiservice.entity.purchase.PurchaseRecord;
import com.apiservice.enums.purchase.PurchaseType;
import com.apiservice.model.car.CarModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class PurchaseRecordModel {

  private static final ModelMapper mapper = new ModelMapper();

  static {
    mapper.createTypeMap(PurchaseRecordModel.class, PurchaseRecord.class, "UpdateEntity")
        .addMappings(m -> m.skip(PurchaseRecord::setId));
  }

  private long id;
  private ZonedDateTime purchaseDate = ZonedDateTime.now();

  @Enumerated(EnumType.STRING)
  private PurchaseType purchaseType;
  private String chassisNo;
  private PurchaseRecordDetailsModel details;

  public static PurchaseRecordModel toModel(PurchaseRecord purchaseRecord) {
    return mapper.map(purchaseRecord, PurchaseRecordModel.class);
  }

  public PurchaseRecord toEntity() {
    return mapper.map(this, PurchaseRecord.class);
  }

  public void updateEntity(PurchaseRecord purchaseRecord) {
    mapper.map(this, purchaseRecord, "UpdateEntity");
  }

}
