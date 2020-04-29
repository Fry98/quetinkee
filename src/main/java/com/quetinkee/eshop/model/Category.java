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

  private boolean active;

  @JsonIgnore
  @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
  public Set<Boquet> boquets;

  public Category() {
  }

  public Category(String name, Integer priority, boolean active) {
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

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
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
