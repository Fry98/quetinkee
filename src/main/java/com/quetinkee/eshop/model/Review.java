package com.quetinkee.eshop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * Unique id for simpler handing
 */
@Entity
@IdClass(ReviewId.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Review implements Serializable {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  @NotNull(message = "Zadejte kytici")
  private Bouquet bouquet;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  @NotNull(message = "Zadejte uživatele")
  private User user;

  @NotBlank(message = "Napište vaši recenzi")
  @Basic(optional = false)
  @Column(columnDefinition="TEXT", nullable = false)
  @Length(max = 2048, message = "Recenze je příliš dlouhá")
  private String message;

  @NotNull(message = "Zadejte hodnocení kytice")
  @Min(value = 0, message = "Příliš malé hodnocení")
  @Max(value = 5, message = "Příliš velké hodnocení")
  private Integer rating;

  private Timestamp created;

  public Review() {
    java.util.Date date = new java.util.Date();
    this.created = new Timestamp(date.getTime());
  }

  public Review(String message, Integer rating) {
    super();
    this.rating = rating;
    this.message = message;
  }

  public Review(User user, String message, Integer rating) {
    this(message, rating);
    this.user = user;
  }

  public Review(Bouquet bouquet, String message, Integer rating) {
    this(message, rating);
    this.bouquet = bouquet;
  }

  public Review(Bouquet bouquet, User user, String message, Integer rating) {
    this(message, rating);
    this.bouquet = bouquet;
    this.user = user;
  }

  public Bouquet getBouquet() {
    return this.bouquet;
  }

  public void setBouquet(Bouquet bouquet) {
    this.bouquet = bouquet;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getRating() {
    return this.rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Timestamp getCreated() {
    return this.created;
  }

  @JsonIgnore
  public void setCreated(Timestamp created) {
    this.created = created;
  }
}