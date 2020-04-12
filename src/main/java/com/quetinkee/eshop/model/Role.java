package com.quetinkee.eshop.model;

public enum Role {
  ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), GUEST("ROLE_COURIER");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}