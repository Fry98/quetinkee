package com.quetinkee.eshop.utils.edit;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.BoquetFlowerCount;
import com.quetinkee.eshop.model.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

public class BoquetEdit implements Serializable {

  @Valid
  private Boquet boquet;

  private List<Integer> keyCategories;

  private List<Integer> keyColors;

  private Map<Integer,Integer> keyFlowerCount;

  public BoquetEdit() {
  }

  public BoquetEdit(Boquet boquet) {
    this.setBoquet(boquet);
    this.updateKeyCategories();
    this.updateKeyColors();
    this.updateKeyFlowerCount();
  }

  public Boquet getBoquet() {
    return this.boquet;
  }

  public void setBoquet(Boquet boquet) {
    this.boquet = boquet;
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
    if (this.boquet.getCategories() != null) {
      this.boquet.getCategories().forEach(category -> { list.add(category.getId()); });
    }
    return list;
  }

  private List<Integer> updateKeyColors() {
    List<Integer> list = new ArrayList<>();
    if (this.boquet.getColors() != null) {
      this.boquet.getColors().forEach(color -> { list.add(color.getValue()); });
    }
    return list;
  }

  private Map<Integer,Integer> updateKeyFlowerCount() {
    HashMap<Integer,Integer> map = new HashMap<>();
    if (this.boquet.getBoquetflowerCount() != null) {
      this.boquet.getBoquetflowerCount().forEach(item -> { map.put(item.getFlower().getId(), item.getCount()); });
    }
    return map;
  }
}
