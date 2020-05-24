package com.quetinkee.eshop.utils.helpers;

import javax.validation.Valid;

/**
 * Support class for creating inventory management
 */
public class FlowerSInStockEdit {

  @Valid
  private Integer id;

  @Valid
  private Integer minCount;

  @Valid
  private Integer free;

  public FlowerSInStockEdit() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getMinCount() {
    return this.minCount;
  }

  public void setMinCount(Integer minCount) {
    this.minCount = minCount;
  }

  public Integer getFree() {
    return this.free;
  }

  public void setFree(Integer free) {
    this.free = free;
  }
}
