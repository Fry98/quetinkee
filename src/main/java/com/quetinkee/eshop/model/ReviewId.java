package com.quetinkee.eshop.model;

import java.io.Serializable;
import java.util.Objects;

public class ReviewId implements Serializable {

  private Bouquet bouquet;
  private User user;

  public ReviewId() {
  }

  public ReviewId(Bouquet bouquet, User user) {
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    final ReviewId other = (ReviewId) obj;
    return this.hashCode() == other.hashCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.bouquet == null ? 0 : this.bouquet.getId(), this.user == null ? 0 : this.user.getId());
  }
}
