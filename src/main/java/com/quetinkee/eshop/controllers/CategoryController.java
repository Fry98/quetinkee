package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.service.CategoryService;
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

@RestController
@RequestMapping("/api/categories")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoryController {

  @Autowired
  private CategoryService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<Category> getPage(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.findAll(page, size, false);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody Category category) {
    service.persist(category);
    return new ResponseEntity(category.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Category getId(@PathVariable("id") Integer id) {
    return this.service.find(id);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Category category) {
    Category original = this.getCategory(id);
    // TODO
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Category category = this.getCategory(id);
    service.delete(category);
    return new ResponseEntity(HttpStatus.OK);
  }

  private Category getCategory(Integer id) throws ResponseStatusException {
    Category category = this.service.find(id);
    if (category == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategorie neexistuje");
    }
    return category;
  }
}
