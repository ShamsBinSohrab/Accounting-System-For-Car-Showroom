package com.apiservice.controller.sell;

import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.model.sell.CarSellRecordFilter;
import com.apiservice.model.sell.CarSellRecordModel;
import com.apiservice.model.sell.CarSellRecordModelAssembler;
import com.apiservice.service.purchase.CarPurchaseRecordService;
import com.apiservice.service.sell.CarSellRecordService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private final CarSellRecordModelAssembler modelAssembler;

  @GetMapping("/sellRecords")
  public CollectionModel<CarSellRecordModel> sellRecords(
          CarSellRecordFilter filter, Pageable pageable) {
    return modelAssembler
            .toCollectionModel(sellRecordService.getAllWithPaginationAndFilter(filter, pageable));
  }

  @GetMapping("/sellRecords/{id}")
  public CarSellRecordModel sellRecord(@PathVariable("id") long id) {
    final CarSellRecord sellRecord = sellRecordService.getById(id);
    return modelAssembler.toModel(sellRecord);
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
    return modelAssembler.toModel(sellRecord);
  }

  @PutMapping("/sellRecords/{id}")
  public CarSellRecordModel update(
      @PathVariable("id") long id, @RequestBody @Valid CarSellRecordModel model) {
    final CarSellRecord sellRecordToUpdate = sellRecordService.getById(id);
    final CarSellRecord sellRecord = model.updateEntity(sellRecordToUpdate);
    sellRecordService.save(sellRecord);
    return modelAssembler.toModel(sellRecord);
  }

  @DeleteMapping("/sellRecords/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") long id) {
    sellRecordService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
