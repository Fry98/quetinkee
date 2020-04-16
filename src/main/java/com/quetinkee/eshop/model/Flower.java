package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Flower extends AbstractEntity {

  @NotBlank(message = "Zadejte název květiny")
  @Basic(optional = false)
  @Column(nullable = false)
  private String name;

  @Basic(optional = true)
  @Column(nullable = true)
  @Lob
  private String description;

  @Digits(integer=11, fraction=2, message = "Cena je ve špatném formátu")
  @Min(value = 1, message = "Zadejte cenu květiny")
  @Basic(optional = false)
  @Column(nullable = false, columnDefinition="DECIMAL(11,2)", precision=11, scale=2)
  private BigDecimal price;

  @JsonIgnore
  @OneToMany(mappedBy = "flower", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<BoquetFlowerCount> flowerCount;

  public Flower() {
  }

  public Flower(String name, String description, String price) {
    this.name = name;
    this.description = description;
    this.setPrice(price);
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

  public Set<BoquetFlowerCount> getBoquetFlowerCount() {
    return this.flowerCount;
  }

  public void addBoquetFlowerCount(BoquetFlowerCount count) {
    Objects.requireNonNull(count);
    if (this.flowerCount == null) {
      this.flowerCount = new HashSet<>();
    }
    count.setFlower(this);
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
      count.setFlower(this);
    }
    this.flowerCount = flowerCount;
  }
}
