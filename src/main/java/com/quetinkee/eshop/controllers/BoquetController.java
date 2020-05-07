package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.service.BoquetService;
import com.quetinkee.eshop.utils.edit.BoquetEdit;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/boquets")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class BoquetController {

  @Autowired
  private BoquetService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<BoquetList> getPage(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.findAll(page, size);
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity create(@Valid @RequestPart("boquet") BoquetEdit boquet, @RequestPart(name =  "blob", required = false) MultipartFile file) {
    this.service.persist(boquet, file);
    return new ResponseEntity(boquet.getBoquet().getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<BoquetList> getList() {
    return this.service.getList();
  }

  @GetMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BoquetEdit getId(@PathVariable("id") Integer id) {
    return new BoquetEdit(this.getBoquet(id));
  }

  @PutMapping(value = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody BoquetEdit boquet) {
    Boquet original = this.getBoquet(id);
    this.service.update(original, boquet);
    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id:[\\d]+}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Boquet boquet = this.getBoquet(id);
    this.service.delete(boquet);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  private Boquet getBoquet(Integer id) throws ResponseStatusException {
    Boquet boquet = this.service.find(id);
    if (boquet == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kytice nenalezena");
    }
    return boquet;
  }
}
