package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FlowersInStock implements Serializable {

    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(unique = true)
    private Flower flower;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer count;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer minCount;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer reserved;

    public FlowersInStock() {
    }

    public FlowersInStock(Flower flower) {
        this.flower = flower;
        this.minCount = 10;
        this.reserved = 0;
    }

    public FlowersInStock(Flower flower, int kkount){
        this.flower = flower;
        this.count = kkount;
        this.minCount = 10;
        this.reserved = 0;
    }

    public Integer getId() {
        return this.id;
    }

    public void setCount(int kkount){
        this.count = kkount;
    }

    public void setMinimalCount(int kkount){
        this.minCount = kkount;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
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

    public int getReserved() {
        return this.reserved;
    }

    public void addReserved(Integer add) {
        this.reserved += add;
    }

    public void freeReserved(Integer sub) {
        this.reserved -= sub;
        if (this.reserved < 0) this.reserved = 0;
    }

    public void consume(Integer num) {
        this.count -= num;
        this.reserved -= num;
        if (this.count < 0) this.count = 0;
        if (this.reserved < 0) this.reserved = 0;
    }
}
