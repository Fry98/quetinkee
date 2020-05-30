package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.model.enums.Size;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Data from shop filter panel
 */
public class FilterRequest {

  private Set<Integer> flowers;

  private Set<Integer> colors;

  @javax.validation.constraints.Size(min=3, max=3, message = "Špatný počet velikostí")
  private Boolean[] sizes;

  private MinMaxPrice prices;

  public FilterRequest() {
  }

  public FilterRequest(Set<Integer> flowers, Set<Integer> colors, Boolean[] sizes, MinMaxPrice prices) {
    this.flowers = flowers;
    this.colors = colors;
    this.sizes = sizes;
    this.prices = prices;
  }

  public Set<Integer> getFlowers() {
    return this.flowers;
  }

  public boolean isFlowers() {
    return this.flowers != null && !this.flowers.isEmpty();
  }

  public void setFlowers(Set<Integer> flowers) {
    this.flowers = flowers;
  }

  public Set<Integer> getColors() {
    return this.colors;
  }

  public boolean isColors() {
    return this.colors != null && !this.colors.isEmpty();
  }

  public void setColors(Set<Integer> colors) {
    this.colors = colors;
  }

  public Set<Size> getSizes() {
    Set items = new HashSet();
    if (this.sizes[0]) items.add(Size.SMALL);
    if (this.sizes[1]) items.add(Size.MEDIUM);
    if (this.sizes[2]) items.add(Size.LARGE);
    return items;
  }

  public boolean isSizes() {
    return this.sizes != null && (this.sizes[0] != false || this.sizes[1] != false || this.sizes[2] != false);
  }

  public void setSizes(Boolean[] sizes) {
    this.sizes = sizes;
  }

  public MinMaxPrice getPrices() {
    return this.prices;
  }

  public BigDecimal getPriceMin() {
    return this.prices.getMin();
  }

  public BigDecimal getPriceMax() {
    return this.prices.getMax();
  }

  public boolean isPrices() {
    return this.prices != null && this.prices.isActive();
  }

  public void setPrices(MinMaxPrice prices) {
    this.prices = prices;
  }
}
