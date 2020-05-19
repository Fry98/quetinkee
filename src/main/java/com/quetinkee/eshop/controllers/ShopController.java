package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.projection.ReviewList;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.enums.Role;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.utils.helpers.BouquetDetail;
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
import com.quetinkee.eshop.model.projection.BouquetList;
import com.quetinkee.eshop.service.security.UserDetail;
import com.quetinkee.eshop.utils.helpers.ReviewSubmit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

  private final int similars = 8;

  @Autowired
  private ShopService shopService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CategoryList> getFilters(Authentication authentication) {
    return this.shopService.findCategories(this.isAdmin(authentication));
  }

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<BouquetList> getSearchResults(@RequestParam(required = true, name = "q") String find, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.shopService.getSeachResults(find, page, size, this.isAdmin(authentication));
  }

  @GetMapping(value = {"/filter","/filter/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public FilterInfo getFilterInfo(@PathVariable(name = "id", required = false) Integer id, Authentication authentication) {
    return this.shopService.getFilterInfo(id, this.isAdmin(authentication));
  }

  @PostMapping(value = {"/filter","/filter/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<BouquetList> getFilterResults(@PathVariable(name = "id", required = false) Integer id, @Valid @RequestBody FilterRequest filter, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.shopService.getFilteredProducts(id, filter, page, size, this.isAdmin(authentication));
  }

  @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<BouquetList> getAllInCategoryId(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    return this.shopService.findBouquetsInCategory(id, page, size, this.isAdmin(authentication));
  }

  @GetMapping(value = "/bouquet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BouquetDetail getId(@PathVariable("id") Integer id, Authentication authentication) {
    BouquetDetail record = this.shopService.findBouquetDetail(id, this.isAdmin(authentication));
    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Záznam nenalezen");
    }
    return record;
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/bouquet/{id}/is-review")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void isBouquetReview(@PathVariable("id") Integer id, Authentication authentication) {
    User user = ((UserDetail) authentication.getDetails()).getUser();
    Bouquet bouquet = this.getRecord(id, authentication);

    if (this.shopService.isBouquetReview(bouquet, user)) {
      throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
    }
  }

  @GetMapping(value = "/bouquet/{id}/similar", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<BouquetList> getBouquetSimilar(@PathVariable("id") Integer id, Authentication authentication) {
    Bouquet bouquet = this.getRecord(id, authentication);
    return this.shopService.getSimilarProducts(bouquet.getId(), this.similars, this.isAdmin(authentication));
  }

  @GetMapping(value = "/bouquet/{id}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<ReviewList> getBouquetReviews(@PathVariable("id") Integer id, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    Bouquet bouquet = this.getRecord(id, authentication);
    return this.shopService.getBouquetReviews(bouquet, page, size);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping(value = "/bouquet/{id}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createBouquetReview(@PathVariable("id") Integer id, @Valid @RequestBody ReviewSubmit review, Authentication authentication) {
    User user = ((UserDetail) authentication.getDetails()).getUser();
    Bouquet bouquet = this.getRecord(id, authentication);
    this.shopService.addBouquetReview(bouquet, user, review);
    return new ResponseEntity(user.getId(), HttpStatus.CREATED);
  }

  private Bouquet getRecord(Integer id, Authentication authentication) throws ResponseStatusException {
    Bouquet record = this.shopService.findBouquet(id, this.isAdmin(authentication));
    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Záznam nenalezen");
    }
    return record;
  }

  private boolean isAdmin(Authentication authentication) {
    return authentication != null && authentication.getAuthorities().toString().contains(Role.ADMIN.toString());
  }
}
