package com.quetinkee.eshop.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(BouquetFlowerCountId.class)
public class BouquetFlowerCount implements Serializable {

  @Id
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn
  @NotNull(message = "Zadejte kytici")
  private Bouquet bouquet;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn
  @NotNull(message = "Zadejte květinu")
  private Flower flower;

  @NotNull(message = "Zadejte počet květin")
  @Min(value = 1, message = "Zadejte počet květin (min. 1)")
  @Basic(optional = false)
  @Column(nullable = false)
  private Integer count;

  public BouquetFlowerCount() {
  }

  public BouquetFlowerCount(Flower flower, Integer count) {
    this.flower = flower;
    this.count = count;
 //   this.flower.addBouquetFlowerCount(this);
  }

  public BouquetFlowerCount(Bouquet bouquet, Integer count) {
    this.bouquet = bouquet;
    this.count = count;
 //   this.bouquet.addBouquetFlowerCount(this);
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

  public Integer getCount() {
    return this.count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
