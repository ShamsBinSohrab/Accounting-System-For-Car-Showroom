package com.apiservice.controller.operator;

import com.apiservice.entity.operator.Operator;
import com.apiservice.service.operator.OperatorService;
import com.apiservice.model.operator.OperatorModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/v1")
@RequiredArgsConstructor
public class OperatorController {

  private final OperatorService operatorService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("/operators")
  public List<OperatorModel> operators() {
    List<OperatorModel> p = operatorService.getAllOperators().stream().map(OperatorModel::toModel).collect(Collectors.toUnmodifiableList());
    return operatorService.getAllOperators().stream()
        .map(OperatorModel::toModel)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/operators/{id}")
  public OperatorModel Operator(@PathVariable long id) {
    Operator operator = operatorService.getOperatorById(id);
    return OperatorModel.toModel(operator);
  }

  @PutMapping("/operators/{id}")
  public OperatorModel update(
          @RequestBody @Valid OperatorModel model, @PathVariable long id) {
    Operator operator = operatorService.getOperatorById(id);
    model.updateEntity(operator,passwordEncoder);
    return OperatorModel.toModel(operatorService.save(operator));
  }

  @DeleteMapping("/operators/{id}")
  public void delete(@PathVariable long id) {
    operatorService.delete(id);
  }

  @PostMapping("/operators")
  @ResponseStatus(HttpStatus.CREATED)
  public OperatorModel create(@RequestBody @Valid OperatorModel operatorModel) {
    final Operator operator = operatorModel.toUsers(passwordEncoder);
    operatorService.save(operator);
    return OperatorModel.toModel(operator);
  }
}
