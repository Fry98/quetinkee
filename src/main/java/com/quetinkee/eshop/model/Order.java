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

    @Column(nullable = false)
    @OneToOne
    private User user;

    @Column(nullable = false)
    @OneToOne
    private OrderAddress address;

    @NotBlank(message = "Objednavka je prazdna")
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Item> contains;

    @JsonIgnore
    @ManyToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private Set<Order> orders;





    public Order() {
        this.user = user;
        this.address = address;
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

    public Set<Item> getContains(){
        return this.contains;
    }

    public void addPosition(Bouquet bouquet, Integer quantity){
        Item pos = new Item(bouquet, quantity);
        if (this.contains == null) {
            this.contains = new HashSet<>();
        }
        this.contains.add(pos);
    }

    public void removePosition(Bouquet bouquet){
        for(Item pos : contains){
            if (bouquet == pos.getBouquet()) {
                contains.remove(pos);
            }
        }
    }

    public void removePosition(Item pos){
        contains.remove(pos);
    }

    public void adjustQuantity(Bouquet bouquet, Integer quantity){
        for(Item pos : contains){
            if (bouquet == pos.getBouquet()) {
                pos.setQuantity(quantity);
            }
        }
    }

    public void adjustQuantity(Item pos , Integer quantity){
        for(Item posi : contains){
            if (pos == posi) {
                posi.setQuantity(quantity);
            }
        }
    }

    public void calculateTotalPrice(){
        this.totalPrice = new BigDecimal(0);
        for(Item pos : contains){
            this.totalPrice.add(pos.getBouquet().getPriceDec()) ;
        }
    }

}
