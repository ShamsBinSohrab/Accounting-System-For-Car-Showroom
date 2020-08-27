package com.apiservice.controller.sell;

import com.apiservice.entity.car.Car;
import com.apiservice.entity.sell.CarSellRecord;
import com.apiservice.model.sell.CarSellRecordModel;
import com.apiservice.service.car.CarService;
import com.apiservice.service.sell.CarSellRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/sellRecords")
    @ResponseStatus(HttpStatus.CREATED)
    public CarSellRecordModel create(
            @RequestBody @Valid CarSellRecordModel model) {
        final CarSellRecord sellRecord = model.toEntity();
        final Car car = carService.getCarByChassisNo(model.getCarChassisNo());
        sellRecord.setCar(car);
        carSellRecordService.save(sellRecord);
        return CarSellRecordModel.toModel(sellRecord);
    }

    @PutMapping("/sellRecords/{id}")
    public CarSellRecordModel update(
            @PathVariable("id") long id,
            @RequestBody @Valid CarSellRecordModel model) {
        final CarSellRecord sellRecordToUpdate = carSellRecordService.getById(id);
        final CarSellRecord sellRecord = model.updateEntity(sellRecordToUpdate);
        final Car car =
                carService.getCarByChassisNo(model.getCarChassisNo());
        sellRecord.setCar(car);
        carSellRecordService.save(sellRecord);
        return CarSellRecordModel.toModel(sellRecord);
    }

    @DeleteMapping("/sellRecords/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        carSellRecordService.delete(id);
    }
}
