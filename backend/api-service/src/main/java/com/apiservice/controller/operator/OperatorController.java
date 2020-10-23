package com.apiservice.controller.operator;

import com.apiservice.entity.master.operator.Operator;
import com.apiservice.model.operator.ChangePasswordModel;
import com.apiservice.model.operator.OperatorModel;
import com.apiservice.model.operator.OperatorModelAssembler;
import com.apiservice.model.operator.PasswordChangeValidator;
import com.apiservice.model.operator.ResetPasswordModel;
import com.apiservice.service.operator.OperatorService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  private final OperatorModelAssembler modelAssembler;

  @GetMapping("/operators")
  public CollectionModel<OperatorModel> list() {
    return modelAssembler.toCollectionModel(operatorService.getAllOperators());
  }

  @GetMapping("/operators/{id}")
  public OperatorModel details(@PathVariable long id) {
    Operator operator = operatorService.getOperatorById(id);
    return modelAssembler.toModel(operator);
  }

  @PostMapping("/operators")
  @ResponseStatus(HttpStatus.CREATED)
  public OperatorModel create(@RequestBody @Valid OperatorModel operatorModel) {
    final Operator operator = operatorModel.toOperator();
    operatorService.createNewOperator(operator);
    return modelAssembler.toModel(operator);
  }

  @PutMapping("/operators/{id}")
  public OperatorModel update(@RequestBody @Valid OperatorModel model, @PathVariable long id) {
    final Operator operator = operatorService.getOperatorById(id);
    model.updateEntity(operator);
    operatorService.save(operator);
    return modelAssembler.toModel(operator);
  }

  @PatchMapping("/operators/{id}/changePassword")
  public ResponseEntity<Void> changePassword(
      @RequestBody @Valid ChangePasswordModel model, @PathVariable long id) {
    final Operator operator = operatorService.getOperatorById(id);
    passwordChangeValidator.validatePasswordChange(model, operator);
    operator.setPassword(model.getNewPassword());
    operatorService.changePassword(operator);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/operators/{id}/resetPassword")
  @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
  public ResponseEntity<Void> resetPassword(
      @RequestBody @Valid ResetPasswordModel model, @PathVariable long id) {
    final Operator operator = operatorService.getOperatorById(id);
    passwordChangeValidator.validatePasswordReset(model);
    operator.setPassword(model.getNewPassword());
    operatorService.changePassword(operator);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/operators/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> delete(@PathVariable long id) {
    operatorService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
