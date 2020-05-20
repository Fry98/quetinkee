package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/stock")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InventoryController {

    @Autowired
    private InventoryService service;
/*
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<Inventory> getPage(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        return this.service.findAll(page, size, false);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody Inventory inventory) {
        service.persist(inventory);
        return new ResponseEntity(inventory.getId(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Inventory getId(@PathVariable("id") Integer id) {
        return this.service.find(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Inventory inventory) {
       Inventory original = this.getInventory(id);
        // TODO
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteId(@PathVariable("id") Integer id) {
        Inventory inventory = this.getInventory(id);
        service.delete(inventory);
        return new ResponseEntity(HttpStatus.OK);
    }
*/
}