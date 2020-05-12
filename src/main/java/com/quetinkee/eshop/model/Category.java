package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category extends AbstractEntity {

  @NotBlank(message = "Zadejte nazev kategorie")
  @Basic(optional = false)
  @Column(nullable = false)
  private String name;

  @Basic(optional = false)
  @Column(nullable = false)
  private Integer priority;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean active = false;

  @JsonIgnore
  @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public Set<Bouquet> bouquets;

  public Category() {
  }

  public Category(String name, Integer priority, Boolean active) {
    this.name = name;
    this.priority = priority;
    this.active = active;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Boolean isActive() {
    return this.active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Set<Bouquet> getBouquets() {
    return this.bouquets;
  }

  public void addBouquet(Bouquet bouquet) {
    Objects.requireNonNull(bouquet);
    if (this.bouquets == null) {
      this.bouquets = new HashSet<>();
    }
    this.bouquets.add(bouquet);
  }

  public void removeBouquet(Bouquet bouquet) {
    Objects.requireNonNull(bouquet);
    if (this.bouquets != null) {
      this.bouquets.remove(bouquet);
    }
  }

  public void setBouquets(Set<Bouquet> bouquets) {
    this.bouquets = bouquets;
  }
}
