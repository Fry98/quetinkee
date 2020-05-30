package com.quetinkee.eshop.model.projection;

import org.springframework.data.annotation.PersistenceConstructor;

/**
 * "Implementation of projection" because its needed for JSON parse
 */
public class OptionList {
  private Integer id;
  private String name;

  public OptionList() {
  }

  @PersistenceConstructor
  public OptionList(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}
