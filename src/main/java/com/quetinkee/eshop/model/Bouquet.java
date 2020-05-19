package com.quetinkee.eshop.model;

import com.quetinkee.eshop.model.enums.Size;
import com.quetinkee.eshop.model.enums.Color;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bouquet extends AbstractEntity {

  @NotBlank(message = "Zadejte název kytice")
  @Basic(optional = false)
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "Zadejte popis kytice")
  @Basic(optional = false)
  @Column(columnDefinition="TEXT", nullable = false)
  private String perex;

  @Column(columnDefinition="TEXT", nullable = true)
  private String description;

  @Column(nullable = true)
  private String path;

  @Column(nullable = true)
  private String image;

  @NotNull(message = "Zadejte cenu kytice")
  @Digits(integer = 11, fraction = 2, message = "Cena je ve špatném formátu")
  @Min(value = 1, message = "Zadejte cenu kytice")
  @Basic(optional = false)
  @Column(nullable = false, columnDefinition = "DECIMAL(11,2)", precision = 11, scale = 2)
  private BigDecimal price;

  @Basic(optional = true)
  @Column(nullable = true, length = 2)
  private Size size;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean active = false;

  @JsonIgnore
  @ElementCollection
  @Column(columnDefinition = "Integer")
  private List<Color> colors;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Category> categories;

  @JsonIgnore
  @OneToMany(mappedBy = "bouquet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<BouquetFlowerCount> bouquetFlowerCount;

  @JsonIgnore
  @OneToMany(mappedBy = "bouquet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Review> reviews;

  public Bouquet() {
  }

  public Bouquet(String name, String perex, String description, String price, Size size, Boolean active) {
    this.name = name;
    this.perex = perex;
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

  public String getPerex() {
    return this.perex;
  }

  public void setPerex(String perex) {
    this.perex = perex;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @JsonProperty
  public String getPath() {
    return this.path;
  }

  @JsonIgnore
  public void setPath(String path) {
    this.path = path;
  }

  @JsonProperty
  public String getImage() {
    return this.image;
  }

  @JsonIgnore
  public void setImage(String image) {
    this.image = image;
  }

  @JsonIgnore
  public BigDecimal getPriceDec() {
    return this.price;
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

  public Size getSize() {
    return this.size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public Boolean isActive() {
    return this.active;
  }

  public void setActive(Boolean active) {
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
    if (!this.categories.contains(category)) {
      this.categories.add(category);
    }
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

  public Set<BouquetFlowerCount> getBouquetFlowerCount() {
    return this.bouquetFlowerCount;
  }

  public void addBouquetFlowerCount(BouquetFlowerCount count) {
    Objects.requireNonNull(count);
    if (this.bouquetFlowerCount == null) {
      this.bouquetFlowerCount = new HashSet<>();
    }
    Optional<BouquetFlowerCount> exists = this.bouquetFlowerCount.stream().filter(
            it -> it.getFlower().getId().equals(count.getFlower().getId())).findAny();
    if (exists.isPresent()) {
      exists.get().setCount(count.getCount());
    }
    else {
      count.setBouquet(this);
      this.bouquetFlowerCount.add(count);
    }
  }

  public void removeBouquetFlowerCount(BouquetFlowerCount count) {
    Objects.requireNonNull(count);
    if (this.bouquetFlowerCount != null) {
      this.bouquetFlowerCount.removeIf( rec -> Objects.equals(rec.getFlower(), count.getFlower()) );
      count.setBouquet(null);
    }
  }

  public void setBouquetFlowerCount(Set<BouquetFlowerCount> flowerCount) {
    this.bouquetFlowerCount = flowerCount;
  }

  public void setColors(List<Color> colors) {
    this.colors = colors;
  }

  public void addColor(Color color) {
    Objects.requireNonNull(color);
    if (this.colors == null) {
      this.colors = new ArrayList<>();
    }
    if (!this.colors.contains(color)) {
      this.colors.add(color);
    }
  }

  public void removeColor(Color color) {
    Objects.requireNonNull(color);
    if (this.colors != null) {
      this.colors.remove(color);
    }
  }

  public List<Color> getColors() {
    return this.colors;
  }

  public Set<Review> getReviews() {
    return this.reviews;
  }

  public void addReview(Review review) {
    Objects.requireNonNull(review);
    if (this.reviews == null) {
      this.reviews = new HashSet<>();
    }

    if (!this.reviews.contains(review)) {
      this.reviews.add(review);
      review.setBouquet(this);
    }
  }

  public void removeReview(Review review) {
    Objects.requireNonNull(review);
    if (this.reviews != null) {
      this.reviews.removeIf( rec -> Objects.equals(rec.getUser(), review.getUser()) );
      review.setBouquet(null);
    }
  }

  public void setReviews(Set<Review> reviews) {
    this.reviews = reviews;
  }
}
