package com.quetinkee.eshop.utils;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UploadException extends ResponseStatusException {

  public UploadException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }

  public UploadException(String reason, IOException ex) {
    super(HttpStatus.BAD_REQUEST, reason, ex);
  }
}