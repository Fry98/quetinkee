package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "USERS_ADDRESS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Address extends AbstractEntity {

  @NotBlank(message = "Ulice je povinná položka")
  @Basic(optional = false)
  @Column(nullable = false)
  private String street;

  @NotBlank(message = "Město je povinná položka")
  @Basic(optional = false)
  @Column(nullable = false)
  private String city;

  @NotBlank(message = "PSČ je povinná položka")
  @Pattern(regexp = "^[0-9]{5}$", message = "Neplatné směrovací číslo")
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

  @JsonIgnore
  @Override
  public Integer getId() {
    return super.getId();
  }

  @JsonIgnore
  @Override
  public void setId(Integer id) {
    super.setId(id);
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