package com.quetinkee.eshop.model;

import java.util.HashMap;

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
  private final static HashMap map = new HashMap<Integer, Color>();

  Color(Integer value) {
    this.value = value;
  }

  static {
    for (Color val : Color.values()) {
      map.put(val.getValue(), val);
    }
  }

  public static Color valueOf (Integer val) {
    return (Color) map.get(val);
  }

  public Integer getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
