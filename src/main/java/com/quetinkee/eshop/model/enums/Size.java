package com.quetinkee.eshop.model.enums;

import java.util.HashMap;

public enum Size {
  SMALL("s"), MEDIUM("m"), LARGE("l");

  private final String value;
  private final static HashMap map = new HashMap<String, Size>();

  Size(String value) {
    this.value = value;
  }

  static {
    for (Size val : Size.values()) {
      map.put(val.getValue(), val);
    }
  }

  public static Size typeOf (String val) {
    return (Size) map.get(val);
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return value;
  }
}
