package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USERS")
@JsonIgnoreProperties(value = "password", allowGetters = false, allowSetters = true)
public class User extends AbstractEntity {

  @NotBlank(message = "Jméno je povinná položka")
  @Basic(optional = false)
  @Column(nullable = false)
  private String firstName;

  @NotBlank(message = "Příjmení je povinná položka")
  @Basic(optional = false)
  @Column(nullable = false)
  private String lastName;

  @NotBlank(message = "E-mail je povinná položka")
  @Email(message = "Nesprávně zadaný email")
  @Basic(optional = false)
  @Column(nullable = false, unique = true)
  private String mail;

  @Basic(optional = false)
  @Column(nullable = false)
  private String password;

  @NotBlank(message = "Telefonní číslo je povinná položka")
  @Basic(optional = false)
  @Column(nullable = false)
  private String phone;

  @Enumerated(EnumType.STRING)
  @Basic(optional = false)
  @Column(nullable = false)
  private Role role;

  @Valid
  @NotNull(message = "Zadejte doručovací adresu")
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(nullable = true)
  private Address addressDelivery = null;

  @Valid
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(nullable = true)
  private Address addressBilling = null;

  @JsonIgnore
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;

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