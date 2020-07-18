package com.apiservice.controller;

import com.apiservice.entity.PurchaseRecord;
import com.apiservice.model.PurchaseRecordCharge;
import com.apiservice.service.PurchaseRecordService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
  public PurchaseRecord create(@RequestBody @Valid PurchaseRecord purchaseRecord) {
    return purchaseRecordService.save(purchaseRecord);
  }

  @PutMapping("/purchaseRecords/{id}")
  public PurchaseRecord update(@PathVariable("id") long id,
      @RequestBody @Valid PurchaseRecord purchaseRecord) {
    return purchaseRecordService.update(id, purchaseRecord);
  }

  @DeleteMapping("/purchaseRecords/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") long id) {
    purchaseRecordService.delete(id);
  }

  @PatchMapping("/purchaseRecords/{id}")
  public PurchaseRecord addCharge(
      @PathVariable("id") long id,
      @RequestBody @Valid PurchaseRecordCharge charge) {
    return purchaseRecordService.addCharge(id, charge);
  }

}