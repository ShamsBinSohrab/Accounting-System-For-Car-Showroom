package com.apiservice.model.car;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.apiservice.authentication.AuthenticationService;
import com.apiservice.authentication.Scopes;
import com.apiservice.controller.car.CarController;
import com.apiservice.controller.purchase.CarPurchaseRecordController;
import com.apiservice.controller.sell.CarSellRecordController;
import com.apiservice.entity.tenant.car.Car;
import com.apiservice.repository.purchase.CarPurchaseRecordRepository;
import com.apiservice.repository.sell.CarSellRecordRepository;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarModelAssembler implements RepresentationModelAssembler<Car, CarModel> {

  private final CarPurchaseRecordRepository purchaseRecordRepository;
  private final CarSellRecordRepository sellRecordRepository;
  private final ModelMapper mapper;
  private final HttpServletRequest request;
  private final AuthenticationService authService;

  @Override
  public CarModel toModel(Car car) {
    final List<Scopes> scopes = authService.extractScopesFromToken(request);
    final CarModel model = mapper.map(car, CarModel.class);
    if(scopes.contains("CAR_READ"))
      addLinkToDetails(model);
    if(scopes.contains("CAR_WRITE"))
    {
      addLinkToCreate(model);
      addLinkToUpdate(model);
      addLinkToDelete(model);
      purchaseRecordRepository
              .findByCarId(car.getId())
              .ifPresentOrElse(
                      pr -> {
                        if (!sellRecordRepository.existsByPurchaseRecordId(pr.getId())) {
                          addLinkToSellRecord(model);
                        }
                      },
                      () -> addLinkToPurchaseRecord(model));
    }
    return model;
  }

  private void addLinkToDetails(CarModel model) {
    model.add(linkTo(methodOn(CarController.class).car(model.getId())).withRel("details"));
  }

  private void addLinkToCreate(CarModel model) {
    model.add(linkTo(methodOn(CarController.class).create(model)).withRel("create"));
  }

  private void addLinkToUpdate(CarModel model) {
    model.add(linkTo(methodOn(CarController.class).update(model, model.getId())).withRel("update"));
  }

  private void addLinkToDelete(CarModel model) {
    model.add(linkTo(methodOn(CarController.class).delete(model.getId())).withRel("delete"));
  }

  private void addLinkToSellRecord(CarModel model) {
    model.add(
        linkTo(methodOn(CarSellRecordController.class).create(model.getId(), null))
            .withRel("sellRecord"));
  }

  private void addLinkToPurchaseRecord(CarModel model) {
    model.add(
        linkTo(methodOn(CarPurchaseRecordController.class).create(model.getId(), null))
            .withRel("purchaseRecord"));
  }
}
