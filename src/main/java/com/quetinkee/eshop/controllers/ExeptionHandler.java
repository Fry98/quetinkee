package com.quetinkee.eshop.controllers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.quetinkee.eshop.utils.ErrorMessage;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExeptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ErrorMessage handleResponseException(ResponseStatusException ex, HttpServletResponse resp) {
    resp.setStatus(ex.getStatus().value());
    if (ex.getReason() != null) return new ErrorMessage(ex.getReason());
    return null;
  }

  @ExceptionHandler(JsonMappingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleJsonException(JsonMappingException ce) {
     return new ErrorMessage(ce.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleValidException(MethodArgumentNotValidException ce) {
    String message = "";
    for (ObjectError e : ce.getBindingResult().getAllErrors()) {
      message = message.concat(e.getDefaultMessage() + "\n");
    }
    return new ErrorMessage(message);
  }
}
