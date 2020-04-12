package com.quetinkee.eshop.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.javamoney.moneta.Money;

@Entity
public class Flower extends AbstractEntity {

  @NotBlank(message = "Zadejte nazev kytice")
  @Basic(optional = false)
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "Zadejte cenu kytice")
  @Basic(optional = false)
  @Column(nullable = false)
  private String price;

  @ManyToMany(mappedBy = "flowers")
  public Set<Boquet> boquets;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Money getMoney() {
    return Money.of(new BigDecimal(this.price), "CZK");
  }

  public String getPrice() {
    return this.price;
  }

  public void setPrice(Money money) {
    this.price = money.getNumberStripped().toString();
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public Set<Boquet> getBoquets() {
    return this.boquets;
  }

  public void addBoquet(Boquet boquet) {
    Objects.requireNonNull(boquet);
    if (this.boquets == null) {
      this.boquets = new HashSet<>();
    }
    this.boquets.add(boquet);
  }

  public void removeBoquet(Boquet boquet) {
    Objects.requireNonNull(boquet);
    if (this.boquets != null) {
      this.boquets.remove(boquet);
    }
  }

  public void setBoquets(Set<Boquet> boquets) {
    this.boquets = boquets;
  }
}
