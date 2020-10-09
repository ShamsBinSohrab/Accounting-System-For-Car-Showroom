package com.apiservice.model.car;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.controller.car.CarController;
import com.apiservice.controller.purchase.CarPurchaseRecordController;
import com.apiservice.controller.sell.CarSellRecordController;
import com.apiservice.entity.tenant.car.Car;
import com.apiservice.repository.purchase.CarPurchaseRecordRepository;
import com.apiservice.repository.sell.CarSellRecordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarModelAssembler implements RepresentationModelAssembler<Car, CarModel> {

  private final CarPurchaseRecordRepository purchaseRecordRepository;
  private final CarSellRecordRepository sellRecordRepository;
  private final ModelMapper modelMapper;

  @Override
  public CarModel toModel(Car car) {
    final CarModel model = modelMapper.map(car, CarModel.class);
    addLinkToDetails(model, car);
    addLinkToUpdate(model, car);
    addLinkToDelete(model, car);
    purchaseRecordRepository
        .findByCarId(car.getId())
        .ifPresentOrElse(
            pr -> {
              if (!sellRecordRepository.existsByPurchaseRecordId(pr.getId())) {
                addLinkToSellRecord(model, car);
              }
            },
            () -> addLinkToPurchaseRecord(model, car));
    return model;
  }

  private void addLinkToDetails(CarModel model, Car car) {
    model.add(linkTo(methodOn(CarController.class).car(car.getId())).withRel("details"));
  }

  private void addLinkToUpdate(CarModel model, Car car) {
    model.add(linkTo(methodOn(CarController.class).update(model, car.getId())).withRel("update"));
  }

  private void addLinkToDelete(CarModel model, Car car) {
    model.add(linkTo(methodOn(CarController.class).delete(car.getId())).withRel("delete"));
  }

  private void addLinkToSellRecord(CarModel model, Car car) {
    model.add(
        linkTo(methodOn(CarSellRecordController.class).create(car.getId(), null))
            .withRel("sellRecord"));
  }

  private void addLinkToPurchaseRecord(CarModel model, Car car) {
    model.add(
        linkTo(methodOn(CarPurchaseRecordController.class).create(car.getId(), null))
            .withRel("purchaseRecord"));
  }
}
