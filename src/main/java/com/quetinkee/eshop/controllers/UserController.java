package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
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
  private UserService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<User> getPage(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.findAll(page, size);
  }

  /**
   * Create new user
   *
   * @param user
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody User user) {
    if (user.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not allowed!");
    }
    this.service.create(user);
    return new ResponseEntity(user.getId(), HttpStatus.CREATED);
  }


  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserList> getList() {
    return this.service.getList();
  }

  /**
   * Get user info by Id
   *
   * @param id
   * @return
   */
  @GetMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User getId(@PathVariable("id") Integer id) {
    return this.getUser(id);
  }

  /**
   * Update user info by Id
   *
   * @param id
   * @param newUser
   * @return
   */
  @PutMapping(value = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody User newUser) {
    User original = this.getUser(id);
    this.service.update(original, newUser);
    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id:[\\d]+}")
  public ResponseEntity delete(@PathVariable("id") Integer id) {
    User user = this.getUser(id);
    this.service.delete(user);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  /**
   * Get user billing address
   *
   * @param id
   * @return
   */
  @GetMapping(value = "/{id:[\\d]+}/billing", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressBillingId(@PathVariable("id") Integer id) {
    User user = this.getUser(id);
    return user.getAddressBilling();
  }

  @GetMapping(value = "/{id:[\\d]+}/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressDeliveryId(@PathVariable("id") Integer id) {
    User user = this.getUser(id);
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
    User user = this.getUser(id);
    if (user.getAddressBilling() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresa neexistuje");
    }
    this.service.updateAddress(user.getAddressBilling(), address);
    return new ResponseEntity(HttpStatus.OK);
  }

  @PutMapping(value = "/{id:[\\d]+}/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressDeliveryId(@PathVariable("id") Integer id, @RequestBody Address address) {
    User user = this.getUser(id);
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
    User user = this.getUser(id);
    if (user.getAddressBilling() != null || address.getId() != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Adresa již existuje");
    }
    this.service.persistAddress(address);
    user.setAddressBilling(address);
    this.service.persist(user);
    return new ResponseEntity(address.getId(), HttpStatus.CREATED);
  }

  @PostMapping(value = "/{id:[\\d]+}/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressDeliveryId(@PathVariable("id") Integer id, @Valid @RequestBody Address address) {
    User user = this.getUser(id);
    if (user.getAddressDelivery() != null || address.getId() != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Adresa již existuje");
    }
    this.service.persistAddress(address);
    user.setAddressDelivery(address);
    this.service.persist(user);
    return new ResponseEntity(address.getId(), HttpStatus.CREATED);
  }

  private User getUser(Integer id) throws ResponseStatusException {
    User user = this.service.find(id);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Uživatel nenalezen");
    }
    return user;
  }
}
