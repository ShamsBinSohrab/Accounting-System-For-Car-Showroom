package com.apiservice.controller.purchase;

import com.apiservice.entity.car.Car;
import com.apiservice.entity.purchase.PurchaseRecord;
import com.apiservice.model.purchase.PurchaseRecordCharge;
import com.apiservice.model.purchase.PurchaseRecordModel;
import com.apiservice.service.car.CarService;
import com.apiservice.service.purchase.PurchaseRecordService;
import java.util.List;
import java.util.stream.Collectors;
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
  public List<PurchaseRecordModel> purchaseRecords() {
    return purchaseRecordService.getAllPurchaseRecord().stream()
        .map(PurchaseRecordModel::toModel)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/purchaseRecords/{id}")
  public PurchaseRecordModel purchaseRecord(@PathVariable("id") long id) {
    return PurchaseRecordModel.toModel(purchaseRecordService.getById(id));
  }

  @PostMapping("/purchaseRecords")
  @ResponseStatus(HttpStatus.CREATED)
  public PurchaseRecordModel create(@RequestBody @Valid PurchaseRecordModel model) {
    PurchaseRecord purchaseRecord = model.toEntity();
    purchaseRecordService.save(purchaseRecord);
    return PurchaseRecordModel.toModel(purchaseRecord);
  }

  @PutMapping("/purchaseRecords/{id}")
  public PurchaseRecordModel update(@PathVariable("id") long id,
      @RequestBody @Valid PurchaseRecordModel model) {
    PurchaseRecord purchaseRecord = purchaseRecordService.getById(id);
    model.updateEntity(purchaseRecord);
    purchaseRecordService.save(purchaseRecord);
    return PurchaseRecordModel.toModel(purchaseRecord);
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
