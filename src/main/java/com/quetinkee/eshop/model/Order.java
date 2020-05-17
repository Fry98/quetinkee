package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order extends AbstractEntity{

    private boolean active;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(nullable = true)
    private User user;

    @Column(nullable = false)
    private String userFirstName;

    @Column(nullable = false)
    private String userLastName;

    @Column(nullable = false)
    private String userPhone;

    @Column(nullable = false)
    private String userMail;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderAddress userAddressBilling;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = true)
    private OrderAddress userAddressDelivery;

    @NotBlank(message = "Objednavka je prazdna")
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<Item> contains;

    public Order() {
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
        contains.removeIf(pos -> bouquet == pos.getBouquet());
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public OrderAddress getUserAddressBilling() {
        return userAddressBilling;
    }

    public OrderAddress getUserAddressDelivery() {
        return userAddressDelivery;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
