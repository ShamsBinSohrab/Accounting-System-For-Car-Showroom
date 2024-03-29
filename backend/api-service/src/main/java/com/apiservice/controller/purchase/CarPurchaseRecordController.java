package com.apiservice.controller.purchase;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.model.purchase.CarPurchaseRecordModel;
import com.apiservice.model.purchase.PurchaseRecordFilter;
import com.apiservice.service.car.CarService;
import com.apiservice.service.purchase.CarPurchaseRecordService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
  private final CarService carService;

  @GetMapping("/purchaseRecords")
  public List<CarPurchaseRecordModel> purchaseRecords(
      PurchaseRecordFilter filter, Pageable pageable) {
    return carPurchaseRecordService.getAllWithPaginationAndFilter(filter, pageable).stream()
        .map(CarPurchaseRecordModel::toModel)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/purchaseRecords/{id}")
  public CarPurchaseRecordModel purchaseRecord(@PathVariable("id") long id) {
    final CarPurchaseRecord purchaseRecord = carPurchaseRecordService.getById(id);
    return CarPurchaseRecordModel.toModel(purchaseRecord);
  }

  @PostMapping("cars/{carId}/purchaseRecords")
  @ResponseStatus(HttpStatus.CREATED)
  public CarPurchaseRecordModel create(
      @PathVariable("carId") long carId, @RequestBody @Valid CarPurchaseRecordModel model) {
    final CarPurchaseRecord purchaseRecord = model.toEntity();
    final Car car = carService.getCarById(carId);
    purchaseRecord.setCar(car);
    carPurchaseRecordService.save(purchaseRecord);
    return CarPurchaseRecordModel.toModel(purchaseRecord);
  }

  @PutMapping("/purchaseRecords/{id}")
  public CarPurchaseRecordModel update(
      @PathVariable("id") long id, @RequestBody @Valid CarPurchaseRecordModel model) {
    final CarPurchaseRecord purchaseRecordToUpdate = carPurchaseRecordService.getById(id);
    final CarPurchaseRecord purchaseRecord = model.updateEntity(purchaseRecordToUpdate);
    carPurchaseRecordService.save(purchaseRecord);
    return CarPurchaseRecordModel.toModel(purchaseRecord);
  }

  @DeleteMapping("/purchaseRecords/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") long id) {
    carPurchaseRecordService.delete(id);
  }
}
