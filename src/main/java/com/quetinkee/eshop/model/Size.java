package com.quetinkee.eshop.model;

public enum Size {
  SMALL("SMALL"), MEDIUM("MEDIUM"), LARGE("LARGE");

  private final String name;

  Size(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
