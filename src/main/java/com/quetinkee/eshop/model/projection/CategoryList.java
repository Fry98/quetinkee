package com.quetinkee.eshop.model.projection;

import org.springframework.data.annotation.PersistenceConstructor;

/**
 * "Implementation of projection" because its needed for JSON parse
 */
public class CategoryList extends AbstractList {

  private String name;
  private Boolean active;

  public CategoryList() {
  }

  @PersistenceConstructor
  public CategoryList(Integer id, String name, Boolean active) {
    this.id = id;
    this.name = name;
    this.active = active;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getActive() {
    return this.active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
