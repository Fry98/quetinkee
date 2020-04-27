package com.quetinkee.eshop.model.projection;

import com.quetinkee.eshop.model.Size;

public interface BoquetList {

  public Integer getId();

  public String getName();

  public Size getSize();

  public String getPrice();

  public boolean isActive();
}