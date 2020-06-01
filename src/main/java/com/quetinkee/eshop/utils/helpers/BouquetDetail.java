package com.quetinkee.eshop.utils.helpers;

import com.quetinkee.eshop.model.Bouquet;
import java.io.Serializable;

/**
 * Support class for bouquet detail with rating and inventory info
 */
public class BouquetDetail implements Serializable {

  private Bouquet bouquet;
  private Float rating;
  private Integer storage;

  public BouquetDetail() {
  }

  public BouquetDetail(Bouquet bouquet, Float rating, Integer storage) {
    this.bouquet = bouquet;
    this.rating = rating;
    this.storage = storage;
  }

  public Bouquet getBouquet() {
    return this.bouquet;
  }

  public Float getRating() {
    return this.rating;
  }

  public Integer getStorage() {
    return this.storage;
  }
}
