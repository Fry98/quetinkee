package com.quetinkee.eshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "USER_ADDRESS")
public class Address extends AbstractEntity {

  @NotBlank(message = "Vyplnte ulici")
  @Basic(optional = false)
  @Column(nullable = false)
  private String street;

  @NotBlank(message = "Vyplnte mesto")
  @Basic(optional = false)
  @Column(nullable = false)
  private String city;

  @NotBlank(message = "Zadejte psc")
  @Basic(optional = false)
  @Column(nullable = false)
  private String zip;

  public Address() {
  }

  public Address(String street, String city, String zip) {
    this.street = street;
    this.city = city;
    this.zip = zip;
  }

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZip() {
    return this.zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }
}