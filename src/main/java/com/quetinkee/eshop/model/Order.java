package com.quetinkee.eshop.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order extends AbstractEntity{
    @NotBlank(message = "Objednavka je prazdna")
    @Basic(optional = false)
    @Column(nullable = false)

    private boolean active;

    @JsonIgnore
    @ManyToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    public Set<Order> orders;

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
}
