package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.dao.AddressDao;
import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.Role;
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
  AddressDao adDao;

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
    adDao.save(adr);
    
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
      throw new VerifyError("Not allowed!");
    }

    service.persist(user);
    return new ResponseEntity(user.getId(), HttpStatus.CREATED);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(@PathVariable("id") Integer id, Authentication authentication) {
    if (!this.checkCurrentOrAdmin(id, authentication)) {
      throw new VerifyError("Access denied");
    }

    User user = this.service.find(id);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User dont exists");
    }
    return user;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @PutMapping(value = "/{id}")
  public String update(@PathVariable("id") Integer id, @RequestBody User user, Authentication authentication) {
    if (!this.checkCurrentOrAdmin(id, authentication)) {
      throw new VerifyError("Access denied");
    }
    if (!this.service.checkPassword(user.getPassword())) {
      throw new VerifyError("Zadejte heslo");
    }
    return "How to handle password";
  }

  /**
   * Check if logged user is accessing to his data or its admin
   * @param id
   * @param authentication
   * @return 
   */
  private boolean checkCurrentOrAdmin (Integer id, Authentication authentication) {
    return authentication.getAuthorities().toString().contains(Role.ADMIN.toString())
            || ((UserDetail)authentication.getDetails()).getUser().getId().equals(id);
  }
}
