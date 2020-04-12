package com.quetinkee.eshop.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.javamoney.moneta.Money;

@Entity
public class Boquet extends AbstractEntity {

  @NotBlank(message = "Zadejte nazev kytice")
  @Basic(optional = false)
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "Zadejte cenu kytice")
  @Basic(optional = false)
  @Column(nullable = false)
  private String price;
  
  private boolean active;
  
  @ManyToMany
  @OrderBy("name")
  private Set<Category> categories;

  @ManyToMany
  @OrderBy("name")
  private Set<Flower> flowers;

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

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Set<Category> getCategories() {
    return this.categories;
  }

  public void addCategory(Category category) {
    Objects.requireNonNull(category);
    if (this.categories == null) {
      this.categories = new HashSet<>();
    }
    this.categories.add(category);
  }

  public void removeCategory(Category category) {
    Objects.requireNonNull(category);
    if (this.categories != null) {
      this.categories.remove(category);      
    }
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  public Set<Flower> getFlowers() {
    return this.flowers;
  }

  public void addFlower(Flower flower) {
    Objects.requireNonNull(flower);
    if (this.flowers == null) {
      this.flowers = new HashSet<>();
    }
    this.flowers.add(flower);
  }

  public void removeFlower(Flower flower) {
    Objects.requireNonNull(flower);
    if (this.flowers != null) {
      this.flowers.remove(flower);      
    }
  }

  public void setFlowers(Set<Flower> flowers) {
    this.flowers = flowers;
  }
}
