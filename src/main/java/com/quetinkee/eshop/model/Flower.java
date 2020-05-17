package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
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
  @Column(columnDefinition="TEXT", nullable = true)
  private String description;

  @Digits(integer=11, fraction=2, message = "Cena je ve špatném formátu")
  @Min(value = 1, message = "Zadejte cenu květiny")
  @Basic(optional = false)
  @Column(nullable = false, columnDefinition="DECIMAL(11,2)", precision=11, scale=2)
  private BigDecimal price;

  @JsonIgnore
  @OneToMany(mappedBy = "flower", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<BouquetFlowerCount> bouquetFlowerCount;

  @JsonIgnore
  @OneToMany(mappedBy = "flower", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<FlowersInStock> flowersInStock;

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
    return this.price == null ? null : this.price.toString();
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setPrice(String price) {
    this.price = (new BigDecimal(price)).setScale(2, BigDecimal.ROUND_CEILING);
  }

  public Set<BouquetFlowerCount> getBouquetFlowerCount() {
    return this.bouquetFlowerCount;
  }

  public void addBouquetFlowerCount(BouquetFlowerCount count) {
    Objects.requireNonNull(count);
    if (this.bouquetFlowerCount == null) {
      this.bouquetFlowerCount = new HashSet<>();
    }
    Optional<BouquetFlowerCount> exists = this.bouquetFlowerCount.stream().filter(
            it -> it.getBouquet().getId().equals(count.getBouquet().getId())).findAny();
    if (exists.isPresent()) {
      exists.get().setCount(count.getCount());
    }
    else {
      count.setFlower(this);
      this.bouquetFlowerCount.add(count);
    }
  }

  public void removeBouquetFlowerCount(BouquetFlowerCount count) {
    Objects.requireNonNull(count);
    if (this.bouquetFlowerCount != null) {
       this.bouquetFlowerCount.removeIf( rec -> Objects.equals(rec.getBouquet(), count.getBouquet()) );
      count.setFlower(null);
    }
  }

  public void setBouquetFlowerCount(Set<BouquetFlowerCount> flowerCount) {
    this.bouquetFlowerCount = flowerCount;
  }
}
