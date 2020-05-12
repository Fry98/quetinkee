package com.quetinkee.eshop.utils.helpers;

import com.quetinkee.eshop.model.Bouquet;
import java.io.Serializable;


public class BouquetDetail implements Serializable {

  private final Bouquet bouquet;
  private final Float rating;
  private final Integer storage;

  public BouquetDetail(Bouquet bouquet, Float rating, Integer storage) {
    this.bouquet = bouquet;
    this.rating = rating;
    this.storage = storage;
  }

  public Bouquet getBouquet() {
    return this.bouquet;
  }

  public Float getRating() {
    return rating;
  }

  public Integer getStorage() {
    return storage;
  }
}
