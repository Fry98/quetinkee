package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import java.util.List;
import java.util.Set;

public class FilterInfo {

  private final List<CategoryList> categories;
  private final Set<OptionList> flowers;
  private final Set<Integer> colors;
  private final Set<Size> sizes;
  private final MinMaxPrice prices;

  public FilterInfo(final List<CategoryList> categories, final Set<OptionList> flowers, final Set<Integer> colors, final Set<Size> sizes, final MinMaxPrice prices) {
    this.categories = categories;
    this.flowers = flowers;
    this.colors = colors;
    this.sizes = sizes;
    this.prices = prices;
  }

  public List<CategoryList> getCategories() {
    return this.categories;
  }

  public Set<OptionList> getFlowers() {
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
