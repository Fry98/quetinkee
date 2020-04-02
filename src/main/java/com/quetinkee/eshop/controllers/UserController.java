package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService service;
  
  @PostMapping("/")
  public String createUser(@RequestBody User user) {
    return "THIS IS THE API";
  }

  // TODO: remove it 
  @GetMapping("/brokeit")
  public User brokeDatabase() {
    User test = new User();
    test.setFirstName("First");
    test.setLastName("Last");
    test.setPassword("password");
    test.setMail("login");

    service.persist(test);
    return test;    
  }
  
  @GetMapping("/current")
  public User getInfo() {
    User test = new User();
    test.setFirstName("First");
    test.setLastName("Last");
    test.setPassword("password");
    test.setMail("login");

    return test;
  }

  @PostMapping("/update/{id}")
  public String updateUser() {
    return "THIS IS THE API";
  }
}
