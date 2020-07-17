package com.apiservice.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PurchaseRecordController {

    private final PurchaseRecordService purchaseRecordService;

    @GetMapping("/purchaseRecords")
    public List<PurchaseRecord> purchaseRecords() {
        return purchaseRecordService.getAllPurchaseRecord();
    }

    @GetMapping("/purchaseRecords/{id}")
    public PurchaseRecord purchaseRecord(@PathVariable("id") long id) {
        return purchaseRecordService.getById(id);
    }

    @PostMapping("/purchaseRecords")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseRecord create(@Valid PurchaseRecord purchaseRecord) {
        purchaseRecordService.save(purchaseRecord);
        return purchaseRecord;
    }

}
