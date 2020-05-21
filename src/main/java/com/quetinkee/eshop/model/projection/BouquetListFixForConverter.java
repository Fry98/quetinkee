package com.quetinkee.eshop.model.projection;

public interface BouquetListFixForConverter extends InterfaceList {

  public String getName();

  public String getPath();

  public String getImage();

  // this is the reason why this file exists
  public String getSize();

  public String getPrice();

  public Boolean getActive();
}