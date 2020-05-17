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
/*
    @JsonIgnore
    @OneToMany(mappedBy = "invetory", fetch = FetchType.LAZY)
    private Set<FlowersInStock> toRestock;
*/
    @JsonIgnore
    @OneToMany(mappedBy = "invetory", fetch = FetchType.LAZY)
    private Set<FlowersInStock> flowersCounts;

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

    public Set<FlowersInStock> getFlowers() {
        return this.flowersCounts;
    }

    public void addFlower(Flower flower ,int count) {
//        Objects.requireNonNull(flower);
//        FlowersInStock newCount = new FlowersInStock(flower);
//        newCount.setCount(count);
//        if (this.flowersCounts == null) {
//            this.flowersCounts = new HashSet<FlowersInStock>();
//        }
//        this.flowersCounts.add(newCount);
    }

    public void setFlowersCount(FlowersInStock flowerCount, int count) {
        Objects.requireNonNull(flowerCount);
        if (this.flowersCounts != null) {
            flowerCount.setCount(count);
        }
    }

    public void setFlowers(Set<FlowersInStock> flowers) {
        this.flowersCounts = flowers;
    }

    public Set<FlowersInStock> getRestockOrder(){
      return null;
//      return this.toRestock;
    }

    public void emptyToRestock(){
  //      this.toRestock.clear();
    }

    public void addFlowerToRestockOrder(Flower flower){
        Objects.requireNonNull(flower);
//        FlowersInStock newCount = new FlowersInStock(flower);
//        newCount.setCount(0);
//        if (this.toRestock == null) {
//            this.toRestock = new HashSet<FlowersInStock>();
//        }
//        this.toRestock.add(newCount);
    }

    public void setFlowerCountInRestockOrder(FlowersInStock flower, int count){
        Objects.requireNonNull(flower);
//        if (this.toRestock != null) {
//            flower.setCount(count);
//        }
    }

    public FlowersInStock findFlowerInStock(Flower flower , Set<FlowersInStock> flowersInStock){
//        for (FlowersInStock position : flowersInStock){
//            if(position.getFlower() == flower){
//                return position;
//            }
//        }
        return null;
    }

    public void checkIfRestockNeeded(){
//        for (FlowersInStock position : this.flowersCounts) {
//            if(position.getCount() < position.getMinimalCount()){
//                if(!toRestock.contains(position)){
//                    FlowersInStock toOrder = new FlowersInStock(position.getFlower(), position.getMinimalCount() - position.getCount());
//                    toRestock.add(toOrder);
//                }
//                else {
//                    FlowersInStock needed = findFlowerInStock(position.getFlower() , toRestock);
//                    if(needed.getCount() < position.getMinimalCount() - position.getCount() ){
//                    setFlowerCountInRestockOrder(needed,position.getMinimalCount() - position.getCount());
//                    }
//                }
//            }
//        }
    }

}
