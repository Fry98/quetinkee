package com.quetinkee.eshop.utils.helpers;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Support class for creating inventory management
 */
public class FlowersInStockEdit {

  @Valid
  @NotNull(message = "Chybí id kytky")
  private Integer id;

  @Valid
  @Min(value = 0)
  private Integer minCount;

  @Valid
  private Integer free;

  public FlowersInStockEdit() {
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
