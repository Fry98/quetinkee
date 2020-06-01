package com.quetinkee.eshop.utils.helpers;

import com.quetinkee.eshop.model.Order;
import com.quetinkee.eshop.model.OrderItem;
import com.quetinkee.eshop.utils.InvalidOrderException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.server.ResponseStatusException;

/**
 * Support class for creating order
 */
public class OrderEdit implements Serializable {

  @Valid
  @NotNull(message = "Chybí objedávka")
  private Order order;

  @Valid
  private Integer userId;

  @Valid
  @NotEmpty(message = "Objednavka je prázdná")
  private Map<Integer,@NotNull(message="Zadejte počet") @Min(value = 1, message="Zadejte počet min. 1") Integer> keyItemCount;

  public OrderEdit() {
  }

  public OrderEdit(Order order) throws ResponseStatusException{
    this.order = order;
    if (!this.checkBouquets()) throw new InvalidOrderException("Tuto objednávku již nelze editovat");

    this.userId = order.getUser() == null ? null : order.getUser().getId();
    this.keyItemCount = this.updateKeyItemCount();
  }

  public Order getOrder() {
    return this.order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Integer getId() {
    if (this.order != null) return this.order.getId();
    return null;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Map<Integer, Integer> getKeyItemCount() {
    return this.keyItemCount;
  }

  public void setKeyItemCount(Map<Integer, Integer> keyItemCount) {
    this.keyItemCount = keyItemCount;
  }

  private boolean checkBouquets() {
    if (this.order.getContains() != null) {
      for (OrderItem item : this.order.getContains()) {
        if (item.getBouquet() == null) return false;
      }
    }
    return true;
  }

  private Map<Integer,Integer> updateKeyItemCount() {
    HashMap<Integer,Integer> map = new HashMap<>();
    if (this.order.getContains() != null) {
      this.order.getContains().forEach(item -> { map.put(item.getBouquet().getId(), item.getQuantity()); });
    }
    return map;
  }
}
