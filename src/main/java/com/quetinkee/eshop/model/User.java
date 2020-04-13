package com.quetinkee.eshop.model;

import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "USER")
public class User extends AbstractEntity {

  @NotBlank(message = "Zadejte Vase jmeno")
  @Basic(optional = false)
  @Column(nullable = false)
  private String firstName;

  @NotBlank(message = "Zadejte Vase prijmeni")
  @Basic(optional = false)
  @Column(nullable = false)
  private String lastName;

  @NotBlank(message = "Zadejte Vas e-mail")
  @Email(message = "Zadany e-mail je neplatny")
  @Basic(optional = false)
  @Column(nullable = false, unique = true)
  private String mail;

  @Basic(optional = false)
  @Column(nullable = false)
  private String password;

  @NotBlank(message = "Zadejte Vas telefon")
  @Basic(optional = false)
  @Column(nullable = false)
  private String phone;

  @Enumerated(EnumType.STRING)
  @Basic(optional = false)
  @Column(nullable = false)
  private Role role;

  @Valid
  @OneToOne
  @JoinColumn(nullable = true)
  private Address addressDelivery = null;

  @Valid
  @OneToOne
  @JoinColumn(nullable = true)
  private Address addressBilling = null;

  public User() {
  }

  public User(String firstName, String lastName, String mail, String password, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.mail = mail;
    this.password = password;
    this.phone = phone;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMail() {
    return this.mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void encodePassword(PasswordEncoder encoder) {
    this.password = encoder.encode(password);
  }

  public void erasePassword() {
    this.password = null;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Address getAddressBilling() {
    return this.addressBilling;
  }

  public void setAddressBilling(Address addressBilling) {
    this.addressBilling = addressBilling;
  }

  public Address getAddressDelivery() {
    return this.addressDelivery;
  }

  public void setAddressDelivery(Address addressDelivery) {
    this.addressDelivery = addressDelivery;
  }

  @Override
  public String toString() {
    return "User{" + this.firstName + " " + this.lastName + "(" + this.mail + ")}";
  }
}