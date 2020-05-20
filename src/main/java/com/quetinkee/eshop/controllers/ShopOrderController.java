package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Order;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.model.projection.OrderList;
import com.quetinkee.eshop.service.OrderService;
import com.quetinkee.eshop.service.security.UserDetail;
import com.quetinkee.eshop.utils.helpers.OrderEdit;
import java.util.Map;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/shop/orders")
@PreAuthorize("isAuthenticated()")
public class ShopOrderController {

  @Autowired
  private OrderService orderService;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<OrderList> getOrdersSlice(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size, Authentication authentication) {
    User user = ((UserDetail) authentication.getDetails()).getUser();
    return this.orderService.getSlice(user.getId(), page, size);
  }

  @PostMapping(value = "/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<Integer,Integer> checkItemsInStock(@Valid @RequestBody Map<Integer,Integer> cart) {
    return this.orderService.checkItemsInStock(cart);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createOrder(@Valid @RequestBody OrderEdit order, Authentication authentication) {
    User user = ((UserDetail) authentication.getDetails()).getUser();
    this.orderService.createShop(order, user);
    return new ResponseEntity(order.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Order getOrderDetail(@PathVariable("id") Integer id, Authentication authentication) {
    User user = ((UserDetail) authentication.getDetails()).getUser();
    Order order = this.orderService.find(id);
    if (order == null || !Objects.equals(order.getUser().getId(), user.getId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Záznam nenalezen");
    }
    return order;
  }
}
