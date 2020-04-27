package com.quetinkee.eshop.model.projection;

import com.quetinkee.eshop.model.Size;
import java.io.Serializable;
import java.math.BigDecimal;

public class BoquetListImpl implements BoquetList, Serializable {

  private Integer Id;
  private String name;
  private BigDecimal price;
  private Size size;
  private boolean active;

  public BoquetListImpl() {
  }

  public BoquetListImpl(Integer Id, String name, BigDecimal price, Size size, boolean active) {
    this.Id = Id;
    this.name = name;
    this.price = price;
    this.size = size;
    this.active = active;
  }

  @Override
  public Integer getId() {
    return this.Id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getPrice() {
    return this.price.toString();
  }

  @Override
  public Size getSize() {
    return this.size;
  }

  @Override
  public boolean isActive() {
    return this.active;
  }

  public void setId(Integer Id) {
    this.Id = Id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}