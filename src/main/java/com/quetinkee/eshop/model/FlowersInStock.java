package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FlowersInStock extends AbstractEntity {

    @Id
    @ManyToOne
    @JoinColumn
    Flower flower;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer count;


    public FlowersInStock() {

    }

    public FlowersInStock(Flower flower) {
        this.flower = flower;
    }

    public FlowersInStock(Flower flower, int kkount){
        this.flower = flower;
        this.count = kkount;
    }

    public void setCount(int kkount){
        this.count = kkount;
    }

    public Flower getFlower(){
        return this.flower;
    }
    public int getCount(){
        return this.count;
    }

}
