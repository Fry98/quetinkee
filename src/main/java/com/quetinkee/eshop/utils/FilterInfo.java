package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.model.enums.Size;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Data for shop filter panel
 */
public class FilterInfo implements Serializable {

  private List<CategoryList> categories;
  private Set<OptionList> flowers;
  private Set<Integer> colors;
  private MinMaxPrice prices;
  private boolean[] sizes;

  public FilterInfo() {
  }

  public FilterInfo(List<CategoryList> categories, Set<OptionList> flowers, Set<Integer> colors, Set<Size> sizes, MinMaxPrice prices) {
    this.categories = categories;
    this.flowers = flowers;
    this.colors = colors;
    this.sizes = this.convertSizes(sizes);
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

  public MinMaxPrice getPrices() {
    return this.prices;
  }

  public boolean[] getSizes() {
    return this.sizes;
  }

  private boolean[] convertSizes(Set<Size> sizes) {
    boolean[] array = new boolean[] {false,false,false};
    for (Size size : sizes) {
      switch(size) {
        case SMALL: array[0] = true; break;
        case MEDIUM: array[1] = true; break;
        case LARGE: array[2] = true; break;
      }
    }
    return array;
  }
}
