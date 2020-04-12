package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.Role;
import com.quetinkee.eshop.service.BoquetService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// TODO add user roles !!!

@RestController
@RequestMapping("/api/boquet")
public class BoquetController {

  @Autowired
  BoquetService service;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody Boquet boquet) {
    service.persist(boquet);
    return new ResponseEntity(boquet.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Boquet getId(@PathVariable("id") Integer id, Authentication authentication) throws ResponseStatusException {
    Boquet boquet = this.service.find(id);
    if (boquet == null || (!boquet.isActive() && authentication.getAuthorities().toString().contains(Role.ADMIN.toString()))) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boquet dont exists");
    }
    return boquet;
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Boquet boquet) throws ResponseStatusException {
    Boquet original = this.service.find(id);
    if (original == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boquet dont exists");
    }
    boquet.setId(id);
    service.update(boquet);
    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Boquet original = this.service.find(id);
    if (original == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category dont exists");
    }
    service.delete(original);
    return new ResponseEntity(HttpStatus.OK);
  }
}
