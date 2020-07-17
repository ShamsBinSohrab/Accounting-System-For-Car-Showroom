package com.apiservice.car;

import java.util.List;
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
public class CarController {

  private final CarService carService;

  @GetMapping("/cars")
  public List<Car> cars() {
    return carService.getAllCars();
  }

  @GetMapping("/cars/{id}")
  public Car getCar(@PathVariable long id) {
    return carService.getCarById(id);
  }

  @PostMapping("/cars")
  @ResponseStatus(HttpStatus.CREATED)
  public Car create(@RequestBody @Valid Car car) {
    return carService.save(car);
  }

  @PutMapping("/cars/{id}")
  public Car update(@RequestBody @Valid Car car, @PathVariable long id) {
    return carService.update(car, id);
  }

  @DeleteMapping("/cars/{id}")
  public void delete(@PathVariable long id) {
    carService.delete(id);
  }
}
