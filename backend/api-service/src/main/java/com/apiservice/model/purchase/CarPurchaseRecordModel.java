package com.apiservice.model.purchase;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.controller.purchase.CarPurchaseRecordController;
import com.apiservice.entity.tenant.car.Car;
import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.enums.purchase.PurchaseType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CarPurchaseRecordModel extends RepresentationModel<CarPurchaseRecordModel> {

  private static final ModelMapper mapper = new ModelMapper();

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
    return mapper.map(carPurchaseRecord, CarPurchaseRecordModel.class).addLinks();
  }

  public CarPurchaseRecord toEntity() {
    return mapper.map(this, CarPurchaseRecord.class);
  }

  public CarPurchaseRecord updateEntity(CarPurchaseRecord source) {
    CarPurchaseRecord purchaseRecord = mapper.map(this, CarPurchaseRecord.class);
    purchaseRecord.setId(source.getId());
    purchaseRecord.getPurchaseRecord().setId(source.getPurchaseRecord().getId());
    purchaseRecord.setCar(source.getCar());
    purchaseRecord.setCreatedBy(source.getCreatedBy());
    purchaseRecord.setCreatedDate(source.getCreatedDate());
    return purchaseRecord;
  }

  private CarPurchaseRecordModel addLinks() {
    add(linkTo(methodOn(CarPurchaseRecordController.class).purchaseRecord(id)).withSelfRel());
    return this;
  }
}
