package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ORDERS_ITEM")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderItem extends AbstractEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Order order;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = true)
    private Bouquet bouquet;

    @NotBlank(message = "Zadejte název kytice")
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Zadejte cenu kytice")
    @Digits(integer = 11, fraction = 2, message = "Cena je ve špatném formátu")
    @Min(value = 1, message = "Zadejte cenu kytice")
    @Basic(optional = false)
    @Column(nullable = false, columnDefinition = "DECIMAL(11,2)", precision = 11, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Prazdný počet")
    @Min(value = 1, message = "K nákupu musí být vybrán alespoň 1 kus")
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer quantity;

    public OrderItem(){
    }

    public OrderItem(Bouquet bouquet, Integer Quantity){
        this.setBouquet(bouquet);
        this.quantity = Quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int Quantity){
        this.quantity = Quantity;
    }

    public Bouquet getBouquet(){
        return this.bouquet;
    }

    public void setBouquet(Bouquet bouquet) {
        this.bouquet = bouquet;
        if (bouquet != null) {
            this.name = this.bouquet.getName();
            this.price = this.bouquet.getPriceDec();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public BigDecimal getPriceDec() {
        return this.price;
    }

    public String getPrice() {
        return this.price == null ? null : this.price.toString();
    }
}
