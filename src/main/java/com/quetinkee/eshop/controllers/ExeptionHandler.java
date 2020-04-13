package com.quetinkee.eshop.controllers;

import javax.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExeptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity handleResponseException(ResponseStatusException ex) {
    return new ResponseEntity(ex.getReason(), ex.getStatus());
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public String handleAllException(AccessDeniedException ce) {
    if (ce.getCause() != null) return ce.getCause().getMessage();
    return ce.getMessage();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleAllException(MethodArgumentNotValidException ce) {
    String msg = "";
    for (ObjectError e : ce.getBindingResult().getAllErrors()) {
      msg = msg.concat(e.getDefaultMessage() + "\n");
    }
    return msg;
  }

  @ExceptionHandler(ServletException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleAllException(ServletException ce) {
    if (ce.getCause() != null) return ce.getCause().getMessage();
    return ce.getMessage();
  }
}
