package com.quetinkee.eshop.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Category extends AbstractEntity {

  @NotBlank(message = "Zadejte nazev kategorie")
  @Basic(optional = false)
  @Column(nullable = false)
  private String name;

  private boolean active;

  @ManyToMany(mappedBy = "categories")
  public Set<Boquet> boquets;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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
