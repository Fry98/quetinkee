package com.quetinkee.eshop.model.projection;

import com.quetinkee.eshop.model.Size;

public interface BouquetList extends InterfaceList {

  public String getName();

  public String getPath();

  public String getImage();

  public Size getSize();

  public String getPrice();

  public Boolean getActive();
}