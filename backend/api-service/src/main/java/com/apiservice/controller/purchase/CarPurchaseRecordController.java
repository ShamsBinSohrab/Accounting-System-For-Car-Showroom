package com.apiservice.controller.purchase;

import com.apiservice.entity.purchase.CarPurchaseRecord;
import com.apiservice.model.purchase.CarPurchaseRecordModel;
import com.apiservice.service.purchase.CarPurchaseRecordService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class CarPurchaseRecordController {

  private final CarPurchaseRecordService carPurchaseRecordService;

  @GetMapping("/purchaseRecords")
  public List<CarPurchaseRecordModel> purchaseRecords() {
    return carPurchaseRecordService.getAll().stream()
        .map(CarPurchaseRecordModel::toModel)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/purchaseRecords/{id}")
  public CarPurchaseRecordModel purchaseRecord(@PathVariable("id") long id) {
    final CarPurchaseRecord purchaseRecord = carPurchaseRecordService.getById(id);
    return CarPurchaseRecordModel.toModel(purchaseRecord);
  }

  @PostMapping("/purchaseRecords")
  @ResponseStatus(HttpStatus.CREATED)
  public CarPurchaseRecordModel create(
      @RequestBody @Valid CarPurchaseRecordModel model) {
    CarPurchaseRecord purchaseRecord = model.toEntity();
    carPurchaseRecordService.save(purchaseRecord, model.getCarChassisNo());
    return CarPurchaseRecordModel.toModel(purchaseRecord);
  }

  @PutMapping("/purchaseRecords/{id}")
  public CarPurchaseRecordModel update(@PathVariable("id") long id,
      @RequestBody @Valid CarPurchaseRecordModel model) {
//    CarPurchaseRecord purchaseRecord = carPurchaseRecordService.getById(id);
//    model.updateEntity(purchaseRecord);
//    carPurchaseRecordService.save(purchaseRecord, model.getCarChassisNo());
//    return CarPurchaseRecordModel.toModel(purchaseRecord);
    //TODO fix purchase record update
    return model;
  }

  @DeleteMapping("/purchaseRecords/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") long id) {
    carPurchaseRecordService.delete(id);
  }
}
