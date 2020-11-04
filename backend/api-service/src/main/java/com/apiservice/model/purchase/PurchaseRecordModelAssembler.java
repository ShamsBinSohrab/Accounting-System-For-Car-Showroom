package com.apiservice.model.purchase;

import com.apiservice.controller.purchase.CarPurchaseRecordController;
import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class PurchaseRecordModelAssembler implements
        RepresentationModelAssembler<CarPurchaseRecord, CarPurchaseRecordModel> {

    private final ModelMapper mapper;

    @Override
    public CarPurchaseRecordModel toModel(CarPurchaseRecord purchaseRecord) {
        final CarPurchaseRecordModel model = mapper.map(purchaseRecord, CarPurchaseRecordModel.class);
        addLinkToDetails(model);
        addLinkToUpdate(model);
        addLinkToDelete(model);
        return model;
    }
    private void addLinkToDetails(CarPurchaseRecordModel model) {
        model.add(linkTo(methodOn(CarPurchaseRecordController.class).purchaseRecord(model.getId()))
                .withRel("details"));
    }
    private void addLinkToUpdate(CarPurchaseRecordModel model) {
        model.add(linkTo(methodOn(CarPurchaseRecordController.class).update(model.getId(), model))
                .withRel("update"));
    }
    private void addLinkToDelete(CarPurchaseRecordModel model) {
        model.add(linkTo(methodOn(CarPurchaseRecordController.class).delete(model.getId()))
                .withRel("delete"));
    }
}
