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
    private String recipientFirstName;

    @Column(nullable = false)
    private String recipientLastName;

    @Column(nullable = false)
    private String recipientPhone;

    @Column(nullable = false)
    @OneToOne
    private OrderAddress address;

    @NotBlank(message = "Objednavka je prazdna")
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Item> contains;






    public Order(User user, OrderAddress address, String recipientFirstName, String recipientLastName, String recipientPhone) {
        this.user = user;
        this.address = address;
        this.recipientFirstName = recipientFirstName;
        this.recipientLastName = recipientLastName;
        this.recipientPhone = recipientPhone;
        this.contains = getContains();
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public String getRecipientLastName() {
        return recipientLastName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public OrderAddress getAddress() {
        return address;
    }

}
