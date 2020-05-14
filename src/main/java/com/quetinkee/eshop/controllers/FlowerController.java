package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.projection.FlowerList;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.service.FlowerService;
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
@RequestMapping("/api/flowers")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class FlowerController {

  @Autowired
  protected FlowerService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<FlowerList> getSlice(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.getSlice(page, size);
  }

  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OptionList> getList() {
    return this.service.getList();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody Flower record) {
    this.service.create(record);
    return new ResponseEntity(record.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flower getId(@PathVariable("id") Integer id) {
    return this.getRecord(id);
  }

  @PatchMapping(value = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Flower record) {
    Flower original = this.getRecord(id);
    this.service.update(original, record);
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @DeleteMapping(value = "/{id:[\\d]+}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Flower record = this.getRecord(id);
    this.service.delete(record);
    return new ResponseEntity(HttpStatus.OK);
  }

  protected Flower getRecord(Integer id) throws ResponseStatusException {
    Flower record = this.service.find(id);
    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Záznam nenalezen");
    }
    return record;
  }
}
