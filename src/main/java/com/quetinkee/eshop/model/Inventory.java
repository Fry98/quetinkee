package com.quetinkee.eshop.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Inventory extends AbstractEntity {

    @NotBlank(message = "Zadejte název inventáře")
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    private boolean active;

    @JsonIgnore
    @ManyToMany(mappedBy = "inventories", fetch = FetchType.LAZY)
    public Set<BouquetCount> boquetsCounts;

    public Inventory(){

    }

    public Inventory(String name, boolean active){
        this.name = name;
        this.active = active;
    }

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

    public Set<BouquetCount> getBoquets() {
        return this.boquetsCounts;
    }

    public void addBoquet(Boquet boquet ,int count) {
        Objects.requireNonNull(boquet);
        BouquetCount newCount = new BouquetCount(boquet);
        newCount.setCount(count);
        if (this.boquetsCounts == null) {
            this.boquetsCounts = new HashSet<BouquetCount>();
        }
        this.boquetsCounts.add(newCount);
    }

    public void setBoquetCount(BouquetCount boquetCount, int count) {
        Objects.requireNonNull(boquetCount);
        if (this.boquetsCounts != null) {
            boquetCount.setCount(count);
        }
    }

    public void setBoquets(Set<BouquetCount> boquets) {
        this.boquetsCounts = boquets;
    }

}
