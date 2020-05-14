package com.quetinkee.eshop.utils;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Slice;

public class SlimSlice<T> implements Serializable {

  private final Slice<T> slice;

  public SlimSlice(final Slice<T> slice) {
    this.slice = slice;
  }

  public List<T> getContent() {
    return this.slice.getContent();
  }

  public boolean isFirst() {
    return this.slice.isFirst();
  }

  public boolean isLast() {
    return this.slice.isLast();
  }

  public boolean isEmpty() {
    return this.slice.isEmpty();
  }

  public Integer getNumberOfElements() {
    return this.slice.getNumberOfElements();
  }

  public Integer getNumber() {
    return this.slice.getNumber();
  }
}
