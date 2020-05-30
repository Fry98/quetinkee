package com.quetinkee.eshop.model.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * Projection implementation for filter
 */
public class MinMaxPrice implements Serializable {

  private BigDecimal min;
  private BigDecimal max;

  public MinMaxPrice() {
  }

  @PersistenceConstructor
  public MinMaxPrice(BigDecimal min, BigDecimal max) {
    this.min = min;
    this.max = max;
  }

  public BigDecimal getMin() {
    return this.min;
  }

  public void setMin(BigDecimal min) {
    this.min = min;
  }

  public BigDecimal getMax() {
    return this.max;
  }

  public void setMax(BigDecimal max) {
    this.max = max;
  }

  @JsonIgnore
  public boolean isActive() {
    return this.min != null && this.max != null;
  }
}
