package com.apiservice.controller.sell;

import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.model.sell.CarSellRecordModel;
import com.apiservice.service.car.CarService;
import com.apiservice.service.purchase.CarPurchaseRecordService;
import com.apiservice.service.sell.CarSellRecordService;
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
public class CarSellRecordController {

  private final CarSellRecordService sellRecordService;
  private final CarPurchaseRecordService purchaseRecordService;

  @GetMapping("/sellRecords")
  public List<CarSellRecordModel> sellRecords() {
    return sellRecordService.getAll().stream()
        .map(CarSellRecordModel::toModel)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/sellRecords/{id}")
  public CarSellRecordModel sellRecord(@PathVariable("id") long id) {
    final CarSellRecord sellRecord = sellRecordService.getById(id);
    return CarSellRecordModel.toModel(sellRecord);
  }

  @PostMapping("cars/{carId}/sellRecords")
  @ResponseStatus(HttpStatus.CREATED)
  public CarSellRecordModel create(
      @PathVariable("carId") long carId,
      @RequestBody @Valid CarSellRecordModel model) {
    final CarSellRecord sellRecord = model.toEntity();
    final CarPurchaseRecord purchaseRecord = purchaseRecordService.getByCarId(carId);
    sellRecord.setPurchaseRecord(purchaseRecord);
    sellRecordService.save(sellRecord);
    return CarSellRecordModel.toModel(sellRecord);
  }

  @PutMapping("/sellRecords/{id}")
  public CarSellRecordModel update(
      @PathVariable("id") long id, @RequestBody @Valid CarSellRecordModel model) {
    final CarSellRecord sellRecordToUpdate = sellRecordService.getById(id);
    final CarSellRecord sellRecord = model.updateEntity(sellRecordToUpdate);
    sellRecordService.save(sellRecord);
    return CarSellRecordModel.toModel(sellRecord);
  }

  @DeleteMapping("/sellRecords/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") long id) {
    sellRecordService.delete(id);
  }
}
