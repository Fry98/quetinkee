package com.quetinkee.eshop.controllers;


import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.service.FlowerService;
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
@RequestMapping("/api/flower")
public class FlowerController {

  @Autowired
  FlowerService service;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody Flower flower) {
    service.persist(flower);
    return new ResponseEntity(flower.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flower getId(@PathVariable("id") Integer id) throws ResponseStatusException {
    Flower flower = this.service.find(id);
    if (flower == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flower dont exists");
    }
    return flower;
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Flower flower) throws ResponseStatusException {
    Flower original = this.service.find(id);
    if (original == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flower dont exists");
    }
    flower.setId(id);
    service.update(flower);
    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Flower original = this.service.find(id);
    if (original == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category dont exists");
    }
    service.delete(original);
    return new ResponseEntity(HttpStatus.OK);
  }
}
