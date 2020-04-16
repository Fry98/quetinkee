package com.quetinkee.eshop.model;

public enum Size {
  SMALL("S"), MEDIUM("M"), LARGE("L");

  private final String name;

  Size(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
