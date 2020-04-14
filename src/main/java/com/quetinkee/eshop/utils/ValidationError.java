package com.quetinkee.eshop.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidationError extends ResponseStatusException {

  public ValidationError(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }
}
