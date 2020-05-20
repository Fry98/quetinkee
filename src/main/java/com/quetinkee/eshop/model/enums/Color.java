package com.quetinkee.eshop.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
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
      map.put(val.toString(), val);
    }
  }

  public static Color typeOf (Integer val) {
    return (Color) map.get(val);
  }

  @JsonValue
  public Integer getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
