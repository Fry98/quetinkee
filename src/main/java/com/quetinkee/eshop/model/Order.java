package com.quetinkee.eshop.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order extends AbstractEntity{

    private boolean active;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @NotBlank(message = "Objednavka je prazdna")
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Position> contains;

    @JsonIgnore
    @ManyToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private Set<Order> orders;

    public Order() {

    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void addOrder(Order order) {
        Objects.requireNonNull(order);
        if (this.orders == null) {
            this.orders = new HashSet<>();
        }
        this.orders.add(order);
    }

    public BigDecimal getTotalPrice(){
        return this.totalPrice;
    }

    public Set<Position> getContains(){
        return this.contains;
    }

    public void addPosition(Boquet boquet, Integer quantity){
        Position pos = new Position(boquet, quantity);
        if (this.contains == null) {
            this.contains = new HashSet<>();
        }
        this.contains.add(pos);
    }

    public void removePosition(Boquet boquet){
        for(Position pos : contains){
            if (boquet == pos.getBoquet()) {
                contains.remove(pos);
            }
        }
    }

    public void removePosition(Position pos){
        contains.remove(pos);
    }

    public void adjustQuantity(Boquet boquet, Integer quantity){
        for(Position pos : contains){
            if (boquet == pos.getBoquet()) {
                pos.setQuantity(quantity);
            }
        }
    }

    public void adjustQuantity(Position pos , Integer quantity){
        for(Position posi : contains){
            if (pos == posi) {
                posi.setQuantity(quantity);
            }
        }
    }

    public void calculateTotalPrice(){
        this.totalPrice = new BigDecimal(0);
        for(Position pos : contains){
            this.totalPrice.add(pos.getBoquet().getPricee()) ;
        }
    }

}
