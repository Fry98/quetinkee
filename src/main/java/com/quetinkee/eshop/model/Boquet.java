package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Boquet extends AbstractEntity {

  @NotBlank(message = "Zadejte název kytice")
  @Basic(optional = false)
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "Zadejte popis kytice")
  @Basic(optional = false)
  @Column(nullable = false)
  @Lob
  private String description;

  @Digits(integer=11, fraction=2, message = "Cena je ve špatném formátu")
  @Min(value = 1, message = "Zadejte cenu kytice")
  @Basic(optional = false)
  @Column(nullable = false, columnDefinition="DECIMAL(11,2)", precision=11, scale=2)
  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  @Basic(optional = false)
  @Column(nullable = false)
  private Size size;

  private boolean active;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @OrderBy("name")
  private Set<Category> categories;

  @OneToMany(mappedBy = "boquet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<BoquetFlowerCount> flowerCount;

  public Boquet() {
  }

  public Boquet(String name, String description, String price, Size size, boolean active) {
    this.name = name;
    this.description = description;
    this.setPrice(price);
    this.size = size;
    this.active = active;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPrice() {
    return this.price.toString();
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setPrice(String price) {
    this.price = (new BigDecimal(price)).setScale(2, BigDecimal.ROUND_CEILING);
  }

  public Size getSize() {
    return this.size;
  }

  public void setSize(Size size) {
    this.size = size;
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

  public Set<BoquetFlowerCount> getBoquetFlowerCount() {
    return this.flowerCount;
  }

  public void addBoquetFlowerCount(BoquetFlowerCount count) {
    Objects.requireNonNull(count);
    if (this.flowerCount == null) {
      this.flowerCount = new HashSet<>();
    }
    count.setBoquet(this);
    this.flowerCount.add(count);
  }

  public void removeBoquetFlowerCount(BoquetFlowerCount count) {
    Objects.requireNonNull(count);
    if (this.flowerCount != null) {
      this.flowerCount.remove(count);
    }
  }

  public void setBoquetFlowerCount(Set<BoquetFlowerCount> flowerCount) {
    for (BoquetFlowerCount count : flowerCount) {
      count.setBoquet(this);
    }
    this.flowerCount = flowerCount;
  }
}
