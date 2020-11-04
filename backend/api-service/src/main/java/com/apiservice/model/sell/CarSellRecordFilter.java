package com.apiservice.model.sell;

import com.apiservice.enums.sell.SellType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.ZonedDateTime;

@Data
public class CarSellRecordFilter {
    private String chassisNo;
    private SellType sellType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime sellDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime sellDateTo;
}
