package com.quetinkee.eshop.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentOptionConverter implements AttributeConverter<PaymentOption, String> {

  @Override
  public String convertToDatabaseColumn(PaymentOption value) {
    if (value == null) return null;
    return value.getValue();
  }

  @Override
  public PaymentOption convertToEntityAttribute(String raw) {
    if (raw == null) return null;
    return PaymentOption.typeOf(raw);
  }
}