package com.quetinkee.eshop.model.enums;

import java.util.HashMap;

public enum PaymentOption {
  CASH("cash"), CARD("card");

  private final String value;
  private final static HashMap map = new HashMap<String, Size>();

  PaymentOption(String value) {
    this.value = value;
  }

  static {
    for (PaymentOption val : PaymentOption.values()) {
      map.put(val.getValue(), val);
    }
  }

  public static PaymentOption typeOf (String val) {
    return (PaymentOption) map.get(val);
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return value;
  }
}
