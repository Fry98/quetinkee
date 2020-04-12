package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {

  @Autowired
  CategoryService service;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody Category category) {
    service.persist(category);
    return new ResponseEntity(category.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Category getId(@PathVariable("id") Integer id) throws ResponseStatusException {
    Category category = this.service.find(id);
    if (category == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category dont exists");
    }
    return category;
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Category category) throws ResponseStatusException {
    Category original = this.service.find(id);
    if (original == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category dont exists");
    }
    category.setId(id);
    service.update(category);
    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Category original = this.service.find(id);
    if (original == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category dont exists");
    }
    service.delete(original);
    return new ResponseEntity(HttpStatus.OK);
  }
}
