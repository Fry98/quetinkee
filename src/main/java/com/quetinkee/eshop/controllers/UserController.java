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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService service;

  // TODO: remove it
  @GetMapping("/brokeit")
  public User brokeDatabase() {
    User test = new User();
    test.setFirstName("First");
    test.setLastName("Last");
    test.setPassword("password");
    test.setMail("test@test.cz");
    test.setPhone("123");

    Address adr = new Address("Ulice", "mesto", "psc");
    test.setAddressBilling(adr);

    service.persist(test);

    return test;
  }

  /**
   * Create new user
   * @param user
   * @return
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(@Valid @RequestBody User user) {
    if (this.service.isRegistred(user.getMail())) {
      throw new VerifyError("Uzivatel se zadanym e-mailem je jiz registrovan");
    }
    if (!this.service.checkPassword(user.getPassword())) {
      throw new VerifyError("Zadejte heslo");
    }
    if (user.getId() != null || user.getRole() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not allowed!");
    }

    service.persist(user);
    return new ResponseEntity(user.getId(), HttpStatus.CREATED);
  }

  /**
   * Get current user info
   * @param authentication
   * @return
   * @throws VerifyError
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(Authentication authentication) throws VerifyError {
    return this.getId(((UserDetail)authentication.getDetails()).getUser().getId());
  }

  /**
   * Get user info by Id
   * @param id
   * @return
   */
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User getId(@PathVariable("id") Integer id) throws ResponseStatusException {
    User user = this.service.find(id);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User dont exists");
    }
    user.erasePassword();
    return user;
  }

  /**
   * Update current user
   * @param user
   * @param authentication
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity update(@RequestBody User user, Authentication authentication) {
    return this.updateId(((UserDetail)authentication.getDetails()).getUser().getId(), user);
  }

  /**
   * Update user info by Id
   * @param id
   * @param user
   * @return
   */
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody User user) throws ResponseStatusException {
    User original = this.service.find(id);
    if (original == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User dont exists");
    }

    if (user.getFirstName() != null) {
      original.setFirstName(user.getFirstName());
    }
    if (user.getLastName() != null) {
      original.setLastName(user.getLastName());
    }
    if (user.getPhone() != null) {
      original.setFirstName(user.getFirstName());
    }

    // password
    if (user.getPassword() != null) {
      if (!this.service.checkPassword(user.getPassword())) {
        throw new VerifyError("Zadejte heslo");
      }
      original.setPassword(user.getPassword());
      this.service.encodePassword(original);
    }

    service.update(original);
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * Return current user billing address
   * @param authentication
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @GetMapping(value = "/billing", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressBilling(Authentication authentication) {
    return this.getAddressBillingId(((UserDetail)authentication.getDetails()).getUser().getId());
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(value = "/{id:[\\d]+}/billing", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressBillingId(@PathVariable("id") Integer id) {
    User user = this.service.find(id);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User dont exists");
    }
    return user.getAddressBilling();
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @GetMapping(value = "/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressDelivery(Authentication authentication) {
    return this.getAddressDeliveryId(((UserDetail)authentication.getDetails()).getUser().getId());
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(value = "/{id:[\\d]+}/delivery", produces = MediaType.APPLICATION_JSON_VALUE)
  public Address getAddressDeliveryId(@PathVariable("id") Integer id) {
    User user = this.service.find(id);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User dont exists");
    }
    return user.getAddressDelivery();
  }

  /**
   * Update current user billing address
   * @param address
   * @param authentication
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @PutMapping(value = "/billing", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressBilling(@RequestBody Address address, Authentication authentication) {
    return this.updateAddressBillingId(((UserDetail)authentication.getDetails()).getUser().getId(), address);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/{id:[\\d]+}/billing", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressBillingId(@PathVariable("id") Integer id, @RequestBody Address address) throws ResponseStatusException {
    User original = this.service.find(id);
    if (original == null || original.getAddressBilling() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address dont exists");
    }
    this.updateAddress(original.getAddressBilling(), address);
    return new ResponseEntity(HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @PutMapping(value = "/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressDelivery(@RequestBody Address address, Authentication authentication) {
    return this.updateAddressDeliveryId(((UserDetail)authentication.getDetails()).getUser().getId(), address);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/{id:[\\d]+}/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateAddressDeliveryId(@PathVariable("id") Integer id, @RequestBody Address address) throws ResponseStatusException {
    User original = this.service.find(id);
    if (original == null || original.getAddressDelivery() == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address dont exists");
    }
    this.updateAddress(original.getAddressDelivery(), address);
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * Create current user billing address
   * @param address
   * @param authentication
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @PostMapping(value = "/billing", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressBilling(@Valid @RequestBody Address address, Authentication authentication) {
    return this.createAddressBillingId(((UserDetail)authentication.getDetails()).getUser().getId(), address);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(value = "/{id:[\\d]+}/billing", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressBillingId(@PathVariable("id") Integer id, @RequestBody Address address) throws ResponseStatusException {
    User original = this.service.find(id);
    if (original == null || original.getAddressBilling() != null || address.getId() != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Address already exists");
    }
    service.persistAddress(address);
    original.setAddressBilling(address);
    service.persist(original);
    return new ResponseEntity(address.getId(), HttpStatus.CREATED);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @PostMapping(value = "/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressDelivery(@Valid @RequestBody Address address, Authentication authentication) {
    return this.createAddressDeliveryId(((UserDetail)authentication.getDetails()).getUser().getId(), address);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping(value = "/{id:[\\d]+}/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAddressDeliveryId(@PathVariable("id") Integer id, @RequestBody Address address) throws ResponseStatusException {
    User original = this.service.find(id);
    if (original == null || original.getAddressDelivery() != null || address.getId() != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Address already exists");
    }
    service.persistAddress(address);
    original.setAddressDelivery(address);
    service.persist(original);
    return new ResponseEntity(address.getId(), HttpStatus.CREATED);
  }

  private void updateAddress (Address original, Address update) {
    if (update.getStreet() != null) {
      original.setStreet(update.getStreet());
    }
    if (update.getCity() != null) {
      original.setCity(update.getCity());
    }
    if (update.getZip() != null) {
      original.setZip(update.getZip());
    }
    service.updateAddress(original);
  }
}
