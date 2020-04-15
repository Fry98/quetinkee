package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.service.FlowerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/flowers")
public class FlowerController {

  @Autowired
  private FlowerService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<Flower> getPage(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.findAll(page, size);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody Flower flower) {
    service.persist(flower);
    return new ResponseEntity(flower.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flower getId(@PathVariable("id") Integer id) {
    return this.getFlower(id);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Flower flower) {
    Flower original = this.getFlower(id);
    // TODO
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Flower flower = this.getFlower(id);
    service.delete(flower);
    return new ResponseEntity(HttpStatus.OK);
  }

  private Flower getFlower(Integer id) throws ResponseStatusException {
    Flower flower = this.service.find(id);
    if (flower == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flower dont exists");
    }
    return flower;
  }
}
