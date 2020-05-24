package com.quetinkee.eshop.model.projection;

public interface FlowersInStockList extends InterfaceList {

  public String getName();

  public Integer getCount();

  public Integer getMinCount();

  public Integer getReserved();

  public Integer getFree();
}