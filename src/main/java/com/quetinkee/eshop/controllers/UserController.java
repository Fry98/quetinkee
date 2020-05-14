package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.model.projection.UserList;
import com.quetinkee.eshop.service.UserService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

  @Autowired
  protected UserService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<UserList> getSlice(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.getSlice(page, size);
  }

  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OptionList> getList() {
    return this.service.getList();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody User record) {
    this.service.create(record);
    return new ResponseEntity(record.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User getId(@PathVariable("id") Integer id) {
    return this.getRecord(id);
  }

  @PatchMapping(value = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public User updateId(@PathVariable("id") Integer id, @Valid @RequestBody User record) {
    User original = this.getRecord(id);
    return this.service.update(original, record);
  }

  @DeleteMapping(value = "/{id:[\\d]+}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    User record = this.getRecord(id);
    this.service.delete(record);
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * Get user billing address
   *
   * @param id
   * @return
   */
  @GetMapping(value = "/{id:[\\d]+}/billing", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressBillingId(@PathVariable("id") Integer id) {
    User user = this.getRecord(id);
    return user.getAddressBilling();
  }

  @GetMapping(value = "/{id:[\\d]+}/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressDeliveryId(@PathVariable("id") Integer id) {
    User user = this.getRecord(id);
    return user.getAddressDelivery();
  }

  /**
   * Update user billing address
   *
   * @param id
   * @param address
   * @return
   */
  @PutMapping(value = "/{id:[\\d]+}/billing", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressBillingId(@PathVariable("id") Integer id, @RequestBody Address address) {
    User user = this.getRecord(id);
    if (user.getAddressBilling() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresa neexistuje");
    }
    this.service.updateAddress(user.getAddressBilling(), address);
    return new ResponseEntity(HttpStatus.OK);
  }

  @PutMapping(value = "/{id:[\\d]+}/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressDeliveryId(@PathVariable("id") Integer id, @RequestBody Address address) {
    User user = this.getRecord(id);
    if (user.getAddressDelivery() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresa neexistuje");
    }
    this.service.updateAddress(user.getAddressDelivery(), address);
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * Create user billing address
   *
   * @param id
   * @param address
   * @return
   */
  @PostMapping(value = "/{id:[\\d]+}/billing", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressBillingId(@PathVariable("id") Integer id, @Valid @RequestBody Address address) {
    User user = this.getRecord(id);
    if (user.getAddressBilling() != null || address.getId() != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Adresa již existuje");
    }
    this.service.persistAddress(address);
    user.setAddressBilling(address);
    this.service.create(user);
    return new ResponseEntity(address.getId(), HttpStatus.CREATED);
  }

  @PostMapping(value = "/{id:[\\d]+}/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressDeliveryId(@PathVariable("id") Integer id, @Valid @RequestBody Address address) {
    User user = this.getRecord(id);
    if (user.getAddressDelivery() != null || address.getId() != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Adresa již existuje");
    }
    this.service.persistAddress(address);
    user.setAddressDelivery(address);
    this.service.create(user);
    return new ResponseEntity(address.getId(), HttpStatus.CREATED);
  }

  protected User getRecord(Integer id) throws ResponseStatusException {
    User record = this.service.find(id);
    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Záznam nenalezen");
    }
    return record;
  }
}
