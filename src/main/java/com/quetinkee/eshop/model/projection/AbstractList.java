package com.quetinkee.eshop.model.projection;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractList implements InterfaceList, Serializable {

  @Value("#{target.id}")
  protected Integer id;

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public Integer getId() {
    return this.id;
  }
}
