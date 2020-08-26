package com.apiservice.utils.exceptions;

import java.text.MessageFormat;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

  private final String message;

  private EntityNotFoundException(String message) {
    super(message);
    this.message = message;
  }

  public static EntityNotFoundException of(Class<?> clazz, long id) {
    return new EntityNotFoundException(
        MessageFormat.format("No {0} found with id {1}", clazz.getSimpleName(), id));
  }

  public static EntityNotFoundException of(Class<?> clazz, String message) {
    return new EntityNotFoundException(message);
  }


}
