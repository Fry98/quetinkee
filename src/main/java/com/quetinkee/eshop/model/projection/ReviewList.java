package com.quetinkee.eshop.model.projection;

import java.sql.Timestamp;

public interface ReviewList {

  public String getUserName();

  public Integer getRating();

  public String getMessage();

  public Timestamp getCreated();
}
