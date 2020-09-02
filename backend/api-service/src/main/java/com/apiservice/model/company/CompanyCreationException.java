package com.apiservice.model.company;

import lombok.Getter;

@Getter
public class CompanyCreationException extends RuntimeException {

  public CompanyCreationException() {
    super("Unable to create company");
  }

}
