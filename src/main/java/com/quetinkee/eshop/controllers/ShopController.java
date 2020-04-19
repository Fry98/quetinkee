package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.Role;
import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.service.ShopService;
import com.quetinkee.eshop.utils.PanelInfo;
import com.quetinkee.eshop.utils.SlimSlice;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

  @Autowired
  private ShopService shopService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Set<CategoryList> getFilters(Authentication authentication) {
    return this.shopService.findCategories(this.isAdmin(authentication));
  }

  @GetMapping(value = {"/search","/search/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public PanelInfo getSearchInfo(@PathVariable(name = "id", required = false) Integer id, Authentication authentication) {
    return this.shopService.getSearchInfo(id, this.isAdmin(authentication));
  }

  @PostMapping(value = {"/search","/search/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public SlimSlice<BoquetList> getSearchResults(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    // TODO
    return null;
  }

  @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public SlimSlice<BoquetList> getAllInCategoryId(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return new SlimSlice<>(this.shopService.findBoquetsInCategory(id, page, size, this.isAdmin(authentication)));
  }

  // TODO: now same in /api/boquets/{id}
  @GetMapping(value = "/boquet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Boquet getId(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.shopService.findBoquet(id, this.isAdmin(authentication));
  }

  private boolean isAdmin(Authentication authentication) {
    return authentication != null && authentication.getAuthorities().toString().contains(Role.ADMIN.toString());
  }
}
