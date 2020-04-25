package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.Role;
import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.service.ShopService;
import com.quetinkee.eshop.utils.FilterInfo;
import com.quetinkee.eshop.utils.FilterRequest;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

  @Autowired
  private ShopService shopService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CategoryList> getFilters(Authentication authentication) {
    return this.shopService.findCategories(this.isAdmin(authentication));
  }

  @GetMapping(value = {"/filter","/filter/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public FilterInfo getFilterInfo(@PathVariable(name = "id", required = false) Integer id, Authentication authentication) {
    return this.shopService.getSearchInfo(id, this.isAdmin(authentication));
  }

  @PostMapping(value = {"/filter","/filter/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<BoquetList> getFilterResults(@PathVariable("id") Integer id, @Valid @RequestBody FilterRequest filter, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.shopService.getFilteredProducts(id, filter, page, size, this.isAdmin(authentication));
  }

  @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<BoquetList> getAllInCategoryId(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.shopService.findBoquetsInCategory(id, page, size, this.isAdmin(authentication));
  }

  @GetMapping(value = "/boquet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Boquet getId(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.shopService.findBoquet(id, this.isAdmin(authentication));
  }

  private boolean isAdmin(Authentication authentication) {
    return authentication != null && authentication.getAuthorities().toString().contains(Role.ADMIN.toString());
  }
}
