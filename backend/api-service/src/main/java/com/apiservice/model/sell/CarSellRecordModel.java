package com.apiservice.model.sell;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.controller.sell.CarSellRecordController;
import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.enums.sell.SellType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CarSellRecordModel extends RepresentationModel<CarSellRecordModel> {

  private static final ModelMapper mapper = new ModelMapper();

  private Long id;

  @JsonProperty("chassisNo")
  private String purchaseRecordCarChassisNo;

  @Enumerated(EnumType.STRING)
  private SellType sellType;

  private ZonedDateTime sellDate;

  @JsonProperty("paidAsAdvance")
  private BigDecimal sellRecordPaidAsAdvance;

  @JsonProperty("paidOnDelivery")
  private BigDecimal sellRecordPaidOnDelivery;

  @JsonProperty("paidAfterDelivery")
  private BigDecimal sellRecordPaidAfterDelivery;

  @JsonProperty("purchaseLetterNo")
  private String sellRecordPurchaseLetterNo;

  public static CarSellRecordModel toModel(CarSellRecord carSellRecord) {
    return mapper.map(carSellRecord, CarSellRecordModel.class).addLinks();
  }

  public CarSellRecord toEntity() {
    return mapper.map(this, CarSellRecord.class);
  }

  public CarSellRecord updateEntity(CarSellRecord source) {
    CarSellRecord sellRecord = mapper.map(this, CarSellRecord.class);
    sellRecord.setId(source.getId());
    sellRecord.getSellRecord().setId(source.getSellRecord().getId());
    sellRecord.setPurchaseRecord(sellRecord.getPurchaseRecord());
    return sellRecord;
  }

  private CarSellRecordModel addLinks() {
    add(linkTo(methodOn(CarSellRecordController.class).sellRecord(id)).withSelfRel());
    return this;
  }
}
