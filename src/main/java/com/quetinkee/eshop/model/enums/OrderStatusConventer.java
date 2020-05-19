package com.quetinkee.eshop.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConventer implements AttributeConverter<OrderStatus, String> {

  @Override
  public String convertToDatabaseColumn(OrderStatus value) {
    if (value == null) return null;
    return value.getValue();
  }

  @Override
  public OrderStatus convertToEntityAttribute(String raw) {
    if (raw == null) return null;
    return OrderStatus.typeOf(raw);
  }
}