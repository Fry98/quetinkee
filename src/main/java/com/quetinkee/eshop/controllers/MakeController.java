package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.BoquetFlowerCount;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Color;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.BoquetService;
import com.quetinkee.eshop.service.CategoryService;
import com.quetinkee.eshop.service.FlowerService;
import com.quetinkee.eshop.service.UserService;
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
  private FlowerService flowerService;

  @Autowired
  private UserService userService;

  // TODO: remove it
  @GetMapping("/item")
  public Boquet addItems() {
    Flower flower1 = new Flower("kytka 1", "", "10");
    flowerService.persist(flower1);
    Flower flower2 = new Flower("kytka 2", "", "20");
    flowerService.persist(flower2);

    Category catA = new Category("skup A", 1, true);
    categoryService.persist(catA);

    Category catB = new Category("skup Be", 2, true);
    categoryService.persist(catB);

    Boquet boqA = new Boquet("Kvetina A", "<p>popis A s <strong>HTML</strong></p>", "", "100.10", Size.SMALL, true);
    boqA.addColor(Color.YELLOW);
    boqA.addCategory(catA);
    BoquetFlowerCount bqfc1 = new BoquetFlowerCount(flower1, 1);
    boqA.addBoquetFlowerCount(bqfc1);
    boquetService.persist(boqA);

    Boquet boqB = new Boquet("Kvetina B", "<p>popis B s <strong>HTML</strong></p>", "<h1>dlouhý text</h1>", "200", Size.MEDIUM, true);
    boqB.addColor(Color.RED);
    BoquetFlowerCount bqfc2 = new BoquetFlowerCount(flower2, 2);
    boqB.addBoquetFlowerCount(bqfc2);
    boqB.addCategory(catB);
    boquetService.persist(boqB);

    Boquet boqC = new Boquet("Kvetina C", "<p>popis C s <strong>HTML</strong></p>", "<h1>dlouhý text</h1>", "9999999.9999", Size.LARGE, true);
    boqC.addColor(Color.PINK);
    boqC.addColor(Color.BLUE);
    boqC.addCategory(catA);
    boqC.addCategory(catB);
    BoquetFlowerCount bqfc3 = new BoquetFlowerCount(flower1, 3);
    BoquetFlowerCount bqfc4 = new BoquetFlowerCount(flower2, 4);
    boqC.addBoquetFlowerCount(bqfc3);
    boqC.addBoquetFlowerCount(bqfc4);
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
