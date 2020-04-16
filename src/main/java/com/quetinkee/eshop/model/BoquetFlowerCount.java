package com.quetinkee.eshop.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BoquetFlowerCount implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn
  private Boquet boquet;

  @Id
  @ManyToOne
  @JoinColumn
  private Flower flower;

  @Basic(optional = false)
  @Column(nullable = false)
  private Integer count;

  public BoquetFlowerCount() {
  }

  public BoquetFlowerCount(Flower flower, Integer count) {
    this.flower = flower;
    this.count = count;
  }

  public BoquetFlowerCount(Boquet boquet, Integer count) {
    this.boquet = boquet;
    this.count = count;
  }

  public Boquet getBoquet() {
    return this.boquet;
  }

  public void setBoquet(Boquet boquet) {
    this.boquet = boquet;
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    final BoquetFlowerCount other = (BoquetFlowerCount) obj;
    return Objects.equals(this.boquet, other.boquet)
            && Objects.equals(this.flower, other.flower)
            && Objects.equals(this.count, other.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.boquet, this.flower, this.count);
  }
}
