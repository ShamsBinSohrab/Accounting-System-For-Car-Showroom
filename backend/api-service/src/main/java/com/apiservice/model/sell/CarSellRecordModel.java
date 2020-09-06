package com.apiservice.model.sell;

import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.enums.sell.SellType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.modelmapper.ModelMapper;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class CarSellRecordModel {

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
        return mapper.map(carSellRecord, CarSellRecordModel.class);
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
}
