package com.apiservice.controller.operator;

import com.apiservice.entity.operator.Operator;
import com.apiservice.model.operator.ChangePasswordModel;
import com.apiservice.model.operator.OperatorModel;
import com.apiservice.model.operator.PasswordChangeValidator;
import com.apiservice.service.operator.OperatorService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
public class OperatorController {

  private final OperatorService operatorService;
  private final PasswordChangeValidator passwordChangeValidator;

  @GetMapping("/operators")
  public List<OperatorModel> list() {
    return operatorService.getAllOperators().stream()
        .map(OperatorModel::toModel)
        .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/operators/{id}")
  public OperatorModel details(@PathVariable long id) {
    Operator operator = operatorService.getOperatorById(id);
    return OperatorModel.toModel(operator);
  }

  @PostMapping("/operators")
  @ResponseStatus(HttpStatus.CREATED)
  public OperatorModel create(@RequestBody @Valid OperatorModel operatorModel) {
    final Operator operator = operatorModel.toOperator();
    operatorService.save(operator);
    return OperatorModel.toModel(operator);
  }

  @PutMapping("/operators/{id}")
  public OperatorModel update(@RequestBody @Valid OperatorModel model, @PathVariable long id) {
    final Operator operator = operatorService.getOperatorById(id);
    model.updateEntity(operator);
    operatorService.update(operator);
    return OperatorModel.toModel(operator);
  }

  @PatchMapping("/operators/{id}/changePassword")
  public void changePassword(
      @RequestBody @Valid ChangePasswordModel model, @PathVariable long id) {
    final Operator operator = operatorService.getOperatorById(id);
    passwordChangeValidator.validate(model, operator);
    operator.setPassword(model.getNewPassword());
    operatorService.save(operator);
  }

  @DeleteMapping("/operators/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id) {
    operatorService.delete(id);
  }
}
