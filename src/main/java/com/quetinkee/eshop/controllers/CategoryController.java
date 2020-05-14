package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public Slice<CategoryList> getSlice(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.getSlice(page, size);
  }

  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OptionList> getList() {
    return this.service.getList();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody Category record) {
    this.service.create(record);
    return new ResponseEntity(record.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Category getId(@PathVariable("id") Integer id) {
    return this.getRecord(id);
  }

  @PatchMapping(value = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Category updateId(@PathVariable("id") Integer id, @Valid @RequestBody Category record) {
    Category original = this.getRecord(id);
    return this.service.update(original, record);
  }

  @DeleteMapping(value = "/{id:[\\d]+}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Category record = this.getRecord(id);
    this.service.delete(record);
    return new ResponseEntity(HttpStatus.OK);
  }

  private Category getRecord(Integer id) throws ResponseStatusException {
    Category record = this.service.find(id);
    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Záznam nenalezen");
    }
    return record;
  }
}
