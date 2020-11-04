package com.apiservice.model.sell;

import com.apiservice.controller.sell.CarSellRecordController;
import com.apiservice.entity.tenant.sell.CarSellRecord;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CarSellRecordModelAssembler implements
        RepresentationModelAssembler<CarSellRecord, CarSellRecordModel> {

    private final ModelMapper mapper;

    @Override
    public CarSellRecordModel toModel(CarSellRecord sellRecord) {
        final CarSellRecordModel model = mapper.map(sellRecord, CarSellRecordModel.class);
        addLinkToDetails(model);
        addLinkToUpdate(model);
        addLinkToDelete(model);
        return model;
    }
    private void addLinkToDetails(CarSellRecordModel model) {
        model.add(linkTo(methodOn(CarSellRecordController.class).sellRecord(model.getId()))
                .withRel("details"));
    }
    private void addLinkToUpdate(CarSellRecordModel model) {
        model.add(linkTo(methodOn(CarSellRecordController.class).update(model.getId(), model))
                .withRel("update"));
    }
    private void addLinkToDelete(CarSellRecordModel model) {
        model.add(linkTo(methodOn(CarSellRecordController.class).delete(model.getId()))
                .withRel("delete"));
    }
}
