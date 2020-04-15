package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.Role;
import com.quetinkee.eshop.service.BoquetService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/boquets")
public class BoquetController {

  @Autowired
  private BoquetService service;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<Boquet> getPage(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.findAll(page, size, false);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody Boquet boquet) {
    service.persist(boquet);
    return new ResponseEntity(boquet.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Boquet getId(@PathVariable("id") Integer id, Authentication authentication) {
    return this.getBoquet(id, this.isAdmin(authentication));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Boquet boquet) {
    Boquet original = this.getBoquet(id, false);
    // TODO
    return new ResponseEntity(HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Boquet boquet = this.getBoquet(id, false);
    service.delete(boquet);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  private Boquet getBoquet(Integer id, boolean activeOnly) throws ResponseStatusException {
    Boquet boquet = this.service.find(id, activeOnly);
    if (boquet == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kytice nenalezena");
    }
    return boquet;
  }

  private boolean isAdmin(Authentication authentication) {
    return authentication != null && authentication.getAuthorities().toString().contains(Role.ADMIN.toString());
  }
}
