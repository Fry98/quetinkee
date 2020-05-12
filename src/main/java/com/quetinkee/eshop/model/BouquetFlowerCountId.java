package com.quetinkee.eshop.model;

import java.io.Serializable;
import java.util.Objects;

public class BouquetFlowerCountId implements Serializable {

  private Bouquet bouquet;
  private Flower flower;

  public BouquetFlowerCountId() {
  }

  public BouquetFlowerCountId(Bouquet bouquet, Flower flower) {
    this.bouquet = bouquet;
    this.flower = flower;
  }

  public Bouquet getBouquet() {
    return this.bouquet;
  }

  public void setBouquet(Bouquet bouquet) {
    this.bouquet = bouquet;
  }

  public Flower getFlower() {
    return this.flower;
  }

  public void setFlower(Flower flower) {
    this.flower = flower;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    final BouquetFlowerCountId other = (BouquetFlowerCountId) obj;
    return this.hashCode() == other.hashCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.bouquet == null ? 0 : this.bouquet.getId(), this.flower == null ? 0 : this.flower.getId());
  }
}
