package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.utils.ErrorMessage;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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
  public ErrorMessage handleResponseException(ResponseStatusException ex, HttpServletResponse resp) {
    resp.setStatus(ex.getStatus().value());
    return new ErrorMessage(ex.getReason());
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

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorMessage handleAccessException(AccessDeniedException ce) {
    if (ce.getCause() != null) return new ErrorMessage(ce.getCause().getMessage());
    return new ErrorMessage(ce.getMessage());
  }
}
