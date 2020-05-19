package com.quetinkee.eshop.model.enums;

import java.util.HashMap;

public enum OrderStatus {
  NEW("new"), READY("rdy"), PENDING("pnd"), FINISH("fin"), STORNO("sto"), DELETE("del");

  private final String value;
  private final static HashMap map = new HashMap<String, Size>();


  OrderStatus(String value) {
    this.value = value;
  }

  static {
    for (OrderStatus val : OrderStatus.values()) {
      map.put(val.getValue(), val);
    }
  }

  public static OrderStatus typeOf (String val) {
    return (OrderStatus) map.get(val);
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return value;
  }
}
