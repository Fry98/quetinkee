package com.quetinkee.eshop.model;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS_ADDRESS")
public class OrderAddress extends Address {

    public OrderAddress() {
      super();
    }

    public OrderAddress(String ulice, String mesto, String psc) {
        super(mesto, psc, psc);
    }
}
