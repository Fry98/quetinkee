package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.BoquetFlowerCount;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Color;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.Role;
import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.BoquetService;
import com.quetinkee.eshop.service.CategoryService;
import com.quetinkee.eshop.service.FlowerService;
import com.quetinkee.eshop.service.UserService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemInitializer {

  @Autowired
  private BoquetService boquetService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private FlowerService flowerService;

  @Autowired
  private UserService userService;

  @PostConstruct
  public void inicialize () {
    String mailAdmin = "admin@admin.cz";
    if (this.userService.isRegistred(mailAdmin)) return;

    System.out.println("------ Install script ------");

    // insert admin
    User admin = new User();
    admin.setFirstName("Name");
    admin.setLastName("Surname");
    admin.setMail(mailAdmin);
    admin.setPassword("heslo");
    admin.setPhone("123456");
    admin.setRole(Role.ADMIN);
    admin.setAddressDelivery(new Address("Street", "City", "12345"));
    this.userService.persist(admin);

    // create categories
    Category catNew = new Category("Nové", 1, true);
    categoryService.persist(catNew);
    Category catAction = new Category("V akci", 2, true);
    categoryService.persist(catAction);
    Category catSeason = new Category("Sezonní nabídka", 3, true);
    categoryService.persist(catSeason);
    Category catBirth = new Category("Narozeniny", 4, true);
    categoryService.persist(catBirth);
    Category catDed = new Category("Pohřeb", 5, true);
    categoryService.persist(catDed);
    Category catHide = new Category("Překvapení", 1, false);
    categoryService.persist(catHide);

    // flowers
    Flower flower1 = new Flower("Růže", "", "10");
    flowerService.persist(flower1);
    Flower flower2 = new Flower("Fialka", "Note", "20");
    flowerService.persist(flower2);
    Flower flower3 = new Flower("Slunečnice", "žlutá", "30");
    flowerService.persist(flower3);

    // boquets
    Boquet boqA = new Boquet("Nejhezčí kytice", "<p>popis A s <strong>HTML</strong></p>", "", "100.10", Size.SMALL, true);
    boqA.addColor(Color.YELLOW);
    boqA.addCategory(catAction);
    boqA.addCategory(catNew);
    BoquetFlowerCount bqfc1 = new BoquetFlowerCount(flower1, 1);
    boqA.addBoquetFlowerCount(bqfc1);
    boquetService.persist(boqA);

    Boquet boqB = new Boquet("Kytice hezká", "<p>popis B s <strong>HTML</strong></p>", "<h1>dlouhý text</h1>", "200", Size.MEDIUM, true);
    boqB.addColor(Color.RED);
    BoquetFlowerCount bqfc2 = new BoquetFlowerCount(flower2, 2);
    boqB.addBoquetFlowerCount(bqfc2);
    boqB.addCategory(catBirth);
    boqB.addCategory(catNew);
    boqB.addCategory(catHide);
    boquetService.persist(boqB);

    Boquet boqC = new Boquet("Kyticke hezčí", "<p>popis C s <strong>HTML</strong></p>", null, "9999999.9999", Size.LARGE, true);
    boqC.addColor(Color.PINK);
    boqC.addColor(Color.BLUE);
    boqC.addCategory(catAction);
    boqC.addCategory(catBirth);
    boqC.addCategory(catNew);
    boqC.addCategory(catHide);
    BoquetFlowerCount bqfc3 = new BoquetFlowerCount(flower1, 3);
    BoquetFlowerCount bqfc4 = new BoquetFlowerCount(flower2, 4);
    boqC.addBoquetFlowerCount(bqfc3);
    boqC.addBoquetFlowerCount(bqfc4);
    boquetService.persist(boqC);

    Boquet boqD = new Boquet("the EGG", "<p>popis C s <strong>HTML</strong></p>", "<h1>dlouhý text</h1>", "9999999.9999", Size.LARGE, false);
    boqD.addColor(Color.LIME);
    boqD.addCategory(catAction);
    boqD.addCategory(catBirth);
    boqD.addCategory(catDed);
    boqD.addCategory(catHide);
    BoquetFlowerCount bqfc5 = new BoquetFlowerCount(flower3, 3);
    boqD.addBoquetFlowerCount(bqfc5);
    boquetService.persist(boqD);
  }
}
