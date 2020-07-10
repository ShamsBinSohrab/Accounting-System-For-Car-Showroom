package com.apiservice.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
