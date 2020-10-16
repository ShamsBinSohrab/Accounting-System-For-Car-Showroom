package com.apiservice.model.operator;

import com.apiservice.entity.master.operator.Operator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperatorModelAssembler implements RepresentationModelAssembler<Operator, OperatorModel> {

  private final ModelMapper mapper;

  @Override
  public OperatorModel toModel(Operator operator) {
    return null;
  }
}
