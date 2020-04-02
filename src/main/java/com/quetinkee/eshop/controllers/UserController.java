package com.quetinkee.eshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @PostMapping("/")
  public String createUser(/*@RequestBody User user*/) {
    return "THIS IS THE API";
  }

  @GetMapping("/current")
  public String getInfo() {
    return "THIS IS THE API";
  }

  @PostMapping("/update/{id}")
  public String updateUser() {
    return "THIS IS THE API";
  }
}
