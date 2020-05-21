package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.BouquetFlowerCount;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.enums.Color;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.FlowersInStock;
import com.quetinkee.eshop.model.Review;
import com.quetinkee.eshop.model.enums.Role;
import com.quetinkee.eshop.model.enums.Size;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.BouquetService;
import com.quetinkee.eshop.service.CategoryService;
import com.quetinkee.eshop.service.FlowerService;
import com.quetinkee.eshop.service.InventoryService;
import com.quetinkee.eshop.service.UserService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class SystemInitializer {

  @Autowired
  private PlatformTransactionManager ptm;

  @Autowired
  private BouquetService bouquetService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private FlowerService flowerService;

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private UserService userService;

  private final String mailAdmin = "admin@admin.cz";


  @PostConstruct
  public void inicialize () {
    if (!this.userService.isRegistred(mailAdmin)) {
      TransactionTemplate txTemp = new TransactionTemplate(this.ptm);
      txTemp.execute((status) -> {
        insertData();
        return null;
      });
    }
  }

  private void insertData () {


    System.out.println("------ Install script ------");

    // insert admin
    User admin = new User();
    admin.setFirstName("Name");
    admin.setLastName("Surname");
    admin.setMail(mailAdmin);
    admin.setPassword("heslo");
    admin.setPhone("123456789");
    admin.setRole(Role.ADMIN);
    admin.setAddressDelivery(new Address("Street", "City", "12345"));
    this.userService.create(admin);

    // create categories
    Category catNew = new Category("Nové", 1, true);
    categoryService.create(catNew);
    Category catAction = new Category("V akci", 2, true);
    categoryService.create(catAction);
    Category catSeason = new Category("Sezonní nabídka", 3, true);
    categoryService.create(catSeason);
    Category catBirth = new Category("Narozeniny", 4, true);
    categoryService.create(catBirth);
    Category catDed = new Category("Pohřeb", 5, true);
    categoryService.create(catDed);
    Category catHide = new Category("Překvapení", 1, false);
    categoryService.create(catHide);

    // flowers
    Flower flower1 = new Flower("Růže", "", "10");
    flowerService.create(flower1);
    Flower flower2 = new Flower("Fialka", "Note", "20");
    flowerService.create(flower2);
    Flower flower3 = new Flower("Slunečnice", "žlutá", "30");
    flowerService.create(flower3);
    Flower flower4 = new Flower("Lilie", "", "8");
    flowerService.create(flower4);

    // bouquets
    Bouquet boqA = new Bouquet("Nejhezčí kytice", "<p>popis A s <strong>HTML</strong></p>", "", "100.10", Size.SMALL, true);
    boqA.addColor(Color.YELLOW);
    boqA.addCategory(catAction);
    boqA.addCategory(catNew);
    BouquetFlowerCount bqfc1 = new BouquetFlowerCount(flower1, 1);
    boqA.addBouquetFlowerCount(bqfc1);
    BouquetFlowerCount bqfx1 = new BouquetFlowerCount(flower2, 1);
    boqA.addBouquetFlowerCount(bqfx1);

    Review rev = new Review("Recenze nejleší květiny", 5);
    rev.setUser(admin);
    boqA.addReview(rev);
    bouquetService.create(boqA);
/*
    boqA.removeReview(rev);
    boqA.removeBouquetFlowerCount(bqfc1);
    bouquetService.create(boqA);
*/

    Bouquet boqB = new Bouquet("Kytice hezká", "<p>popis B s <strong>HTML</strong></p>", "<h1>dlouhý text</h1>", "200", Size.MEDIUM, true);
    boqB.addColor(Color.RED);
    BouquetFlowerCount bqfc2 = new BouquetFlowerCount(flower2, 2);
    boqB.addBouquetFlowerCount(bqfc2);
    boqB.addCategory(catBirth);
    boqB.addCategory(catNew);
    boqB.addCategory(catHide);
    bouquetService.create(boqB);

    Bouquet boqC = new Bouquet("Kyticke hezčí", "<p>popis C s <strong>HTML</strong></p>", null, "9999999.9999", Size.LARGE, true);
    boqC.addColor(Color.PINK);
    boqC.addColor(Color.BLUE);
    boqC.addCategory(catAction);
    boqC.addCategory(catBirth);
    boqC.addCategory(catNew);
    boqC.addCategory(catHide);
    BouquetFlowerCount bqfc3 = new BouquetFlowerCount(flower1, 3);
    BouquetFlowerCount bqfc4 = new BouquetFlowerCount(flower2, 4);
    boqC.addBouquetFlowerCount(bqfc3);
    boqC.addBouquetFlowerCount(bqfc4);
    bouquetService.create(boqC);

    Bouquet boqD = new Bouquet("the EGG", "<p>popis C s <strong>HTML</strong></p>", "<h1>dlouhý text</h1>", "9999999.9999", Size.LARGE, false);
    boqD.addColor(Color.LIME);
    boqD.addCategory(catAction);
    boqD.addCategory(catBirth);
    boqD.addCategory(catDed);
    boqD.addCategory(catHide);
    BouquetFlowerCount bqfc5 = new BouquetFlowerCount(flower3, 3);
    BouquetFlowerCount bqfc6 = new BouquetFlowerCount(flower4, 1);
    boqD.addBouquetFlowerCount(bqfc5);
    boqD.addBouquetFlowerCount(bqfc6);
    bouquetService.create(boqD);


    // storage
    FlowersInStock fl1 = new FlowersInStock(flower1, 4);
    inventoryService.persist(fl1);
    FlowersInStock fl2 = new FlowersInStock(flower2, 10);
    inventoryService.persist(fl2);
    FlowersInStock fl3 = new FlowersInStock(flower3, 15);
    inventoryService.persist(fl3);

  }
}
