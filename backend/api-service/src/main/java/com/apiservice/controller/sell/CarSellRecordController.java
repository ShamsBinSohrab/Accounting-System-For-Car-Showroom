package com.apiservice.controller.sell;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.entity.tenant.sell.CarSellRecord;
import com.apiservice.model.sell.CarSellRecordModel;
import com.apiservice.service.car.CarService;
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

  private final CarSellRecordService carSellRecordService;
  private final CarService carService;

  @GetMapping("/sellRecords")
  public List<CarSellRecordModel> sellRecords() {
    return carSellRecordService.getAll().stream()
        .map(CarSellRecordModel::toModel)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/sellRecords/{id}")
  public CarSellRecordModel sellRecord(@PathVariable("id") long id) {
    final CarSellRecord sellRecord = carSellRecordService.getById(id);
    return CarSellRecordModel.toModel(sellRecord);
  }

  @PostMapping("cars/{carId}/sellRecords")
  @ResponseStatus(HttpStatus.CREATED)
  public CarSellRecordModel create(
      @PathVariable("carId") long carId,
      @RequestBody @Valid CarSellRecordModel model) {
    final CarSellRecord sellRecord = model.toEntity();
    final Car car = carService.getCarById(carId);
    sellRecord.setCar(car);
    carSellRecordService.save(sellRecord);
    return CarSellRecordModel.toModel(sellRecord);
  }

  @PutMapping("/sellRecords/{id}")
  public CarSellRecordModel update(
      @PathVariable("id") long id, @RequestBody @Valid CarSellRecordModel model) {
    final CarSellRecord sellRecordToUpdate = carSellRecordService.getById(id);
    final CarSellRecord sellRecord = model.updateEntity(sellRecordToUpdate);
    carSellRecordService.save(sellRecord);
    return CarSellRecordModel.toModel(sellRecord);
  }

  @DeleteMapping("/sellRecords/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") long id) {
    carSellRecordService.delete(id);
  }
}
