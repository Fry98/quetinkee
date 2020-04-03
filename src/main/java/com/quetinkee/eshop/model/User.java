package com.quetinkee.eshop.model;

import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
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
  @Column(nullable = false, unique = true)
  private String mail;

  @NotBlank(message = "Zvolte heslo")
  @Column(nullable = false)
  private String password;

  @NotBlank(message = "Zadejte Vas telefon")
  @Column(nullable = false)
  private String phone;

  @Enumerated(EnumType.STRING)
  private Role role;

  public User() {
    this.role = Role.GUEST;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
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
    return password;
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

  @Override
  public String toString() {
    return "User{" + firstName + " " + lastName + "(" + mail + ")}";
  }
}