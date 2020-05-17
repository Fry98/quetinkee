package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.BouquetFlowerCount;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Color;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.BouquetService;
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
  private BouquetService bouquetService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private FlowerService flowerService;

  @Autowired
  private UserService userService;

  // TODO: remove it
  @GetMapping("/item")
  public Bouquet addItems() {
    Flower flower1 = new Flower("kytka 1", "", "10");
    flowerService.create(flower1);
    Flower flower2 = new Flower("kytka 2", "", "20");
    flowerService.create(flower2);

    Category catA = new Category("skup A", 1, true);
    categoryService.create(catA);

    Category catB = new Category("skup Be", 2, true);
    categoryService.create(catB);

    Bouquet boqA = new Bouquet("Kvetina A", "<p>popis A s <strong>HTML</strong></p>", "", "100.10", Size.SMALL, true);
    boqA.addColor(Color.YELLOW);
    boqA.addCategory(catA);
    BouquetFlowerCount bqfc1 = new BouquetFlowerCount(flower1, 1);
    boqA.addBouquetFlowerCount(bqfc1);
    bouquetService.create(boqA);

    Bouquet boqB = new Bouquet("Kvetina B", "<p>popis B s <strong>HTML</strong></p>", "<h1>dlouhý text</h1>", "200", Size.MEDIUM, true);
    boqB.addColor(Color.RED);
    BouquetFlowerCount bqfc2 = new BouquetFlowerCount(flower2, 2);
    boqB.addBouquetFlowerCount(bqfc2);
    boqB.addCategory(catB);
    bouquetService.create(boqB);

    Bouquet boqC = new Bouquet("Kvetina C", "<p>popis C s <strong>HTML</strong></p>", "<h1>dlouhý text</h1>", "9999999.9999", Size.LARGE, true);
    boqC.addColor(Color.PINK);
    boqC.addColor(Color.BLUE);
    boqC.addCategory(catA);
    boqC.addCategory(catB);
    BouquetFlowerCount bqfc3 = new BouquetFlowerCount(flower1, 3);
    BouquetFlowerCount bqfc4 = new BouquetFlowerCount(flower2, 4);
    boqC.addBouquetFlowerCount(bqfc3);
    boqC.addBouquetFlowerCount(bqfc4);
    bouquetService.create(boqC);

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
