package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item extends AbstractEntity {

    @JsonIgnore
    @NotBlank(message = "Prazdná položka")
    @OneToOne(fetch = FetchType.LAZY)
    public Bouquet bouquet;

    @NotBlank(message = "Prazdný počet")
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer Quantity;

    public Item(){

    };

    public Item(Bouquet bouquet, Integer Quantity){
        this.bouquet = bouquet;
        this.Quantity = Quantity;
    };

    public void setQuantity(int Quantity){
        this.Quantity = Quantity;
    };

    public Bouquet getBouquet(){
        return this.bouquet;
    }

    public Integer getQuantity(){
        return this.Quantity;
    }

}
