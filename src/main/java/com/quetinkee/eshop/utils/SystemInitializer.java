package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.model.Role;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.UserService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemInitializer {
  
  @Autowired
  private UserService userService;
  
  @PostConstruct
  public void inicialize () {
    System.out.println("------ Install script ------");
    
    // insert admin
    User admin = new User();
    admin.setFirstName("Name");
    admin.setLastName("Surname");
    admin.setMail("admin@admin.cz");
    admin.setPassword("heslo");
    admin.setPhone("123456");
    admin.setRole(Role.ADMIN);
    this.userService.persist(admin);
  }
}
