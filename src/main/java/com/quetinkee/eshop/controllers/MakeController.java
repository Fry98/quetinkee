package com.quetinkee.eshop.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.BoquetService;
import com.quetinkee.eshop.service.CategoryService;
import com.quetinkee.eshop.service.UserService;
import com.quetinkee.eshop.utils.JSViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: remove it

@RestController
@RequestMapping("/api/make")
public class MakeController {

  @Autowired
  private BoquetService boquetService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private UserService userService;

  // TODO: remove it
  @GetMapping("/item")
  public Boquet addItems() {
    Category catA = new Category("skup A", true);
    categoryService.persist(catA);

    Category catB = new Category("skup Be", true);
    categoryService.persist(catB);

    Boquet boqA = new Boquet("Kvetina A", "<p>popis A s <strong>HTML</strong></p>", "100.10", true);
    boqA.addCategory(catA);
    boquetService.persist(boqA);

    Boquet boqB = new Boquet("Kvetina B", "<p>popis B s <strong>HTML</strong></p>", "200", true);
    boqB.addCategory(catB);
    boquetService.persist(boqB);

    Boquet boqC = new Boquet("Kvetina C", "<p>popis C s <strong>HTML</strong></p>", "9999999.9999", true);
    boqC.addCategory(catA);
    boqC.addCategory(catB);
    boquetService.persist(boqC);

    return boqA;
  }

  @GetMapping("/user")
  public User brokeDatabase() {
    User test = new User();
    test.setFirstName("First");
    test.setLastName("Last");
    test.setPassword("password");
    test.setMail("test@test.cz");
    test.setPhone("123");

    Address adr = new Address("Ulice", "mesto", "psc");
    test.setAddressDelivery(adr);

    this.userService.createRegistred(test);

    return test;
  }
}
