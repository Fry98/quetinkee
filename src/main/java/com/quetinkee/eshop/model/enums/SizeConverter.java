package com.quetinkee.eshop.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Support class for Size enum db conversion - save only value not full string name
 */
@Converter(autoApply = true)
public class SizeConverter implements AttributeConverter<Size, String> {

  @Override
  public String convertToDatabaseColumn(Size value) {
    if (value == null) return null;
    return value.getValue();
  }

  @Override
  public Size convertToEntityAttribute(String raw) {
    if (raw == null) return null;
    return Size.typeOf(raw);
  }
}