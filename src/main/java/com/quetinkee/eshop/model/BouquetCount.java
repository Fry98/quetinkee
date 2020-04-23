package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BouquetCount extends AbstractEntity {

    @Id
    @ManyToOne
    @JoinColumn
    Boquet bouquet;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer count;


    public BouquetCount() {

    }

    public BouquetCount(Boquet boquet) {
        this.bouquet = boquet;
    }

    public BouquetCount(Boquet boquet, int kkount){
        this.bouquet = boquet;
        this.count = kkount;
    }

    public void setCount(int kkount){
        this.count = kkount;
    }

    public Boquet getBouquet(){
        return this.bouquet;
    }
    public int getCount(){
        return this.count;
    }

}
