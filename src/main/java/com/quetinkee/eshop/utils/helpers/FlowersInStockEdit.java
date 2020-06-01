package com.quetinkee.eshop.utils.helpers;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Support class for creating inventory management
 */
public class FlowersInStockEdit {

  @Valid
  @Min(value = 0, message = "Minimální počet může být min. 0")
  private Integer minCount;

  @Valid
  private Integer free;

  public FlowersInStockEdit() {
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
