package com.quetinkee.eshop.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidOrderException extends ResponseStatusException {

  public InvalidOrderException(String reason) {
    super(HttpStatus.FORBIDDEN, reason);
  }
}
