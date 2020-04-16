package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Role;
import com.quetinkee.eshop.service.BoquetService;
import com.quetinkee.eshop.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

  @Autowired
  private BoquetService boquetService;

  @Autowired
  private CategoryService categoryService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Category> getFilters(Authentication authentication) {
    return this.categoryService.findAll(this.isAdmin(authentication));
  }

  @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<Boquet> getAllInCategoryId(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.boquetService.findAllInCategory(id, page, size, this.isAdmin(authentication));
  }

  // TODO: now same in /api/boquets/{id}
  @GetMapping(value = "/boquet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Boquet getId(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.boquetService.find(id, this.isAdmin(authentication));
  }

  private boolean isAdmin(Authentication authentication) {
    return authentication != null && authentication.getAuthorities().toString().contains(Role.ADMIN.toString());
  }
}
