package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.UserService;
import com.quetinkee.eshop.service.security.UserDetail;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  @Autowired
  private UserService service;

  /**
   * Create new user
   *
   * @param user
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody User user) {
    this.service.createRegistred(user);
    return new ResponseEntity(user.getId(), HttpStatus.CREATED);
  }

  /**
   * Get current user info
   *
   * @param authentication
   * @return
   */
  @PreAuthorize("isAuthenticated()")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(Authentication authentication) {
    return this.getCurrentUser(authentication);
  }

  /**
   * Update current user
   *
   * @param newUser
   * @param authentication
   * @return
   */
  @PreAuthorize("isAuthenticated()")
  @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public User update(@RequestBody User newUser, Authentication authentication) {
    User original = this.getCurrentUser(authentication);
    return this.service.update(original, newUser);
  }

  @PostMapping(value = "/forgot", produces = MediaType.APPLICATION_JSON_VALUE)
  public void forgotPassword(@RequestBody User newUser) {
    // TODO
  }

  @PostMapping(value = "/forgot/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void forgotPasswordActivate(@PathVariable("id") String token) {
    // TODO
  }

  /**
   * Return current user billing address
   *
   * @param authentication
   * @return
   */
  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/billing", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressBilling(Authentication authentication) {
    User user = this.getCurrentUser(authentication);
    return user.getAddressBilling();
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressDelivery(Authentication authentication) {
    User user = this.getCurrentUser(authentication);
    return user.getAddressDelivery();
  }

  /**
   * Update current user billing address
   *
   * @param address
   * @param authentication
   * @return
   */
  @PreAuthorize("isAuthenticated()")
  @PatchMapping(value = "/billing", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressBilling(@RequestBody Address address, Authentication authentication) {
    User user = this.getCurrentUser(authentication);
    if (user.getAddressBilling() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresa neexistuje");
    }
    this.service.updateAddress(user.getAddressBilling(), address);
    return new ResponseEntity(HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping(value = "/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressDelivery(@RequestBody Address address, Authentication authentication) {
    User user = this.getCurrentUser(authentication);
    if (user.getAddressDelivery() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresa neexistuje");
    }
    this.service.updateAddress(user.getAddressDelivery(), address);
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * Create current user billing address
   *
   * @param address
   * @param authentication
   * @return
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping(value = "/billing", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressBilling(@Valid @RequestBody Address address, Authentication authentication) {
    User user = this.getCurrentUser(authentication);
    if (user.getAddressBilling() != null || address.getId() != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Adresa již existuje");
    }
    this.service.persistAddress(address);
    user.setAddressBilling(address);
    this.service.create(user);
    return new ResponseEntity(address.getId(), HttpStatus.CREATED);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping(value = "/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressDelivery(@Valid @RequestBody Address address, Authentication authentication) {
    User user = this.getCurrentUser(authentication);
    if (user.getAddressDelivery() != null || address.getId() != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Adresa již existuje");
    }
    this.service.persistAddress(address);
    user.setAddressDelivery(address);
    this.service.create(user);
    return new ResponseEntity(address.getId(), HttpStatus.CREATED);
  }

  private User getCurrentUser(Authentication authentication) throws ResponseStatusException {
    User user = this.service.find(((UserDetail) authentication.getDetails()).getUser().getId());
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Uživatel nenalezen");
    }
    return user;
  }
}
