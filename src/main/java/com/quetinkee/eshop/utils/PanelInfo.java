package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.projection.FlowerList;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import java.util.Set;

public class PanelInfo {

  private final Set<CategoryList> categories;
  private final Set<FlowerList> flowers;
  private final Set<Integer> colors;
  private final Set<Size> sizes;
  private final MinMaxPrice prices;

  public PanelInfo(final Set<CategoryList> categories, final Set<FlowerList> flowers, final Set<Integer> colors, final Set<Size> sizes, final MinMaxPrice prices) {
    this.categories = categories;
    this.flowers = flowers;
    this.colors = colors;
    this.sizes = sizes;
    this.prices = prices;
  }

  public Set<CategoryList> getCategories() {
    return this.categories;
  }

  public Set<FlowerList> getFlowers() {
    return this.flowers;
  }

  public Set<Integer> getColors() {
    return this.colors;
  }

  public boolean[] getSizes() {
    boolean[] array = new boolean[] {false,false,false};
    for (Size size : this.sizes) {
      switch(size) {
        case SMALL: array[0] = true; break;
        case MEDIUM: array[1] = true; break;
        case LARGE: array[2] = true; break;
      }
    }
    return array;
  }

  public MinMaxPrice getPrices() {
    return this.prices;
  }
}