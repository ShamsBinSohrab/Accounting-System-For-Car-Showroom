package com.apiservice.controller.car;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.model.car.CarFilter;
import com.apiservice.model.car.CarModel;
import com.apiservice.model.car.CarModelAssembler;
import com.apiservice.service.car.CarService;
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
public class CarController {

  private final CarService carService;
  private final CarModelAssembler modelAssembler;

  @GetMapping("/cars")
  public CollectionModel<CarModel> cars(CarFilter filter, Pageable pageable) {
    return modelAssembler
        .toCollectionModel(carService.getAllWithPaginationAndFilter(filter, pageable));
  }

  @GetMapping("/cars/{id}")
  public CarModel car(@PathVariable long id) {
    return modelAssembler.toModel(carService.getCarById(id));
  }

  @PostMapping("/cars")
  @ResponseStatus(HttpStatus.CREATED)
  public CarModel create(@RequestBody @Valid CarModel model) {
    final Car car = model.toEntity();
    return modelAssembler.toModel(carService.save(car));
  }

  @PutMapping("/cars/{id}")
  public CarModel update(
      @RequestBody @Valid CarModel model, @PathVariable long id) {
    Car car = carService.getCarById(id);
    model.updateEntity(car);
    return modelAssembler.toModel(carService.save(car));
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    carService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
