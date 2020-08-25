package com.apiservice.controller.purchase;

import com.apiservice.entity.car.Car;
import com.apiservice.entity.purchase.CarPurchaseRecord;
import com.apiservice.model.car.CarModel;
import com.apiservice.model.purchase.PurchaseRecordCar;
import com.apiservice.model.purchase.PurchaseRecordModel;
import com.apiservice.service.car.CarService;
import com.apiservice.service.purchase.PurchaseRecordService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  private final CarService carService;

  @GetMapping("/purchaseRecords")
  public List<PurchaseRecordModel> purchaseRecords() {
    return purchaseRecordService.getAll().stream()
        .map(PurchaseRecordModel::toModel)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/purchaseRecords/{id}")
  public PurchaseRecordModel purchaseRecord(@PathVariable("id") long id) {
    final CarPurchaseRecord purchaseRecord = purchaseRecordService.getById(id);
    return PurchaseRecordModel.toModel(purchaseRecord);
  }

  @PostMapping("/purchaseRecords")
  @ResponseStatus(HttpStatus.CREATED)
  public PurchaseRecordModel create(
      @RequestBody @Valid PurchaseRecordModel model,
      PurchaseRecordCar purchaseRecordCar) {
    CarPurchaseRecord purchaseRecord = model.toEntity();
    Car car = purchaseRecordCar.isDraftCar()
        ? CarModel.newDraftCar(model.getCarChassisNo())
        : carService.getByChassisNo(model.getCarChassisNo());
    purchaseRecord.setCar(car);
    purchaseRecordService.save(purchaseRecord);
    return PurchaseRecordModel.toModel(purchaseRecord);
  }
//
//  @PutMapping("/purchaseRecords/{id}")
//  public PurchaseRecordModel update(@PathVariable("id") long id,
//      @RequestBody @Valid PurchaseRecordModel model) {
//    PurchaseRecord purchaseRecord = purchaseRecordService.getById(id);
//    model.updateEntity(purchaseRecord);
//    purchaseRecordService.save(purchaseRecord);
//    return PurchaseRecordModel.toModel(purchaseRecord);
//  }
//
//  @DeleteMapping("/purchaseRecords/{id}")
//  @ResponseStatus(HttpStatus.NO_CONTENT)
//  public void delete(@PathVariable("id") long id) {
//    purchaseRecordService.delete(id);
//  }
//
//  @PatchMapping("/purchaseRecords/{id}")
//  public PurchaseRecord addCharge(
//      @PathVariable("id") long id,
//      @RequestBody @Valid PurchaseRecordCharge charge) {
//    return purchaseRecordService.addCharge(id, charge);
//  }
}
