package com.quetinkee.eshop.model;

public enum Color {
    WHITE(0),
    YELLOW(1),
    ORANGE(2),
    RED(3),
    PINK(4),
    PURLE(5),
    BLUE(6),
    LIGHTBLUE(7),
    LIME(8),
    GREEN(9);

  private final Integer value;

  Color(Integer value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
