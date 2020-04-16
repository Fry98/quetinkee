package com.quetinkee.eshop.utils;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import org.springframework.data.domain.*;

public class JSSlice<T> extends PageImpl<T> {

  public JSSlice(final List<T> content, final Pageable pageable, final long total) {
    super(content, pageable, total);
  }

  public JSSlice(final List<T> content) {
    super(content);
  }

  public JSSlice(final Page<T> page, final Pageable pageable) {
    super(page.getContent(), pageable, page.getTotalElements());
  }
/*
  @JsonView(JsonViews.UiView.class)
  public int getTotalPages() {
    return super.getTotalPages();
  }

  @JsonView(JsonViews.UiView.class)
  public long getTotalElements() {
    return super.getTotalElements();
  }

  @JsonView(JsonViews.UiView.class)
  public boolean hasNext() {
    return super.hasNext();
  }

  @JsonView(JsonViews.UiView.class)
  public boolean isLast() {
    return super.isLast();
  }

  @JsonView(JsonViews.UiView.class)
  public boolean hasContent() {
    return super.hasContent();
  }
*/
  @JsonView(JSViews.List.class)
  public List<T> getContent() {
    return super.getContent();
  }
}
