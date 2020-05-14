package com.quetinkee.eshop.model.projection;

import java.math.BigDecimal;

/**
 * Projection implementation for filter
 */
public class MinMaxPriceImpl implements MinMaxPrice {

  private BigDecimal min;
  private BigDecimal max;

  public MinMaxPriceImpl() {
  }

  @Override
  public BigDecimal getMin() {
    return this.min;
  }

  public void setMin(BigDecimal min) {
    this.min = min;
  }

  @Override
  public BigDecimal getMax() {
    return this.max;
  }

  public void setMax(BigDecimal max) {
    this.max = max;
  }

  public boolean isActive() {
    return this.min != null && this.max != null;
  }
}
