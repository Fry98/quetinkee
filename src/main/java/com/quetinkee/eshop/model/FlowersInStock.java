package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FlowersInStock extends AbstractEntity {

    @Id
    @ManyToOne
    @JoinColumn
    private Inventory invetory;

    @Id
    @ManyToOne
    @JoinColumn
    private Flower flower;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer count;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer minCount;


    public FlowersInStock() {

    }

    public FlowersInStock(Flower flower) {
        this.flower = flower;
        this.minCount = 10;
    }

    public FlowersInStock(Flower flower, int kkount){
        this.flower = flower;
        this.count = kkount;
        this.minCount = 10;
    }

    public void setCount(int kkount){
        this.count = kkount;
    }

    public void setMinimalCount(int kkount){
        this.minCount = kkount;
    }

    public int getMinimalCount(){
        return this.minCount;
    }

    public Flower getFlower(){
        return this.flower;
    }

    public int getCount(){
        return this.count;
    }

}
