package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.utils.FilterRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

interface FilterDynamic {

  public Slice<BoquetList> findResults(boolean showAll, Integer id, FilterRequest request, Pageable pageable);
}
