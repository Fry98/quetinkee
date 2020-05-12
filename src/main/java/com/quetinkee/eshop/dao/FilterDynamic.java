package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.utils.FilterRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import com.quetinkee.eshop.model.projection.BouquetList;

interface FilterDynamic {

  public Slice<BouquetList> findResults(boolean showAll, Integer id, FilterRequest request, Pageable pageable);
}
