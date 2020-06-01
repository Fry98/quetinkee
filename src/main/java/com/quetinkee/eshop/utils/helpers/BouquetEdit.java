package com.quetinkee.eshop.utils.helpers;

import com.quetinkee.eshop.model.Bouquet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Support class for bouquet editing
 */
public class BouquetEdit implements Serializable {

  @Valid
  @NotNull(message = "Chybí kytice")
  private Bouquet bouquet;

  @Valid
  private List<Integer> keyCategories;

  @Valid
  private List<Integer> keyColors;

  @Valid
  private Map<Integer,@NotNull(message="Zadejte počet") @Min(value = 1, message="Zadejte počet min. 1") Integer> keyFlowerCount;

  public BouquetEdit() {
  }

  public BouquetEdit(Bouquet bouquet) {
    this.bouquet = bouquet;
    this.keyCategories = this.updateKeyCategories();
    this.keyColors = this.updateKeyColors();
    this.keyFlowerCount = this.updateKeyFlowerCount();
  }

  public Bouquet getBouquet() {
    return this.bouquet;
  }

  public void setBouquet(Bouquet bouquet) {
    this.bouquet = bouquet;
  }

  public Integer getId() {
    if (this.bouquet != null) return this.bouquet.getId();
    return null;
  }

  public List<Integer> getKeyCategories() {
    return this.keyCategories;
  }

  public void setKeyCategories(List<Integer> keyCategories) {
    this.keyCategories = keyCategories;
  }

  public List<Integer> getKeyColors() {
    return this.keyColors;
  }

  public void setKeyColors(List<Integer> keyColors) {
    this.keyColors = keyColors;
  }

  public Map<Integer, Integer> getKeyFlowerCount() {
    return this.keyFlowerCount;
  }

  public void setKeyFlowerCount(Map<Integer, Integer> keyFlowerCount) {
    this.keyFlowerCount = keyFlowerCount;
  }

  private List<Integer> updateKeyCategories() {
    List<Integer> list = new ArrayList<>();
    if (this.bouquet.getCategories() != null) {
      this.bouquet.getCategories().forEach(category -> { list.add(category.getId()); });
    }
    return list;
  }

  private List<Integer> updateKeyColors() {
    List<Integer> list = new ArrayList<>();
    if (this.bouquet.getColors() != null) {
      this.bouquet.getColors().forEach(color -> { list.add(color.getValue()); });
    }
    return list;
  }

  private Map<Integer,Integer> updateKeyFlowerCount() {
    HashMap<Integer,Integer> map = new HashMap<>();
    if (this.bouquet.getBouquetFlowerCount() != null) {
      this.bouquet.getBouquetFlowerCount().forEach(item -> { map.put(item.getFlower().getId(), item.getCount()); });
    }
    return map;
  }
}
