package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Bouquet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.quetinkee.eshop.model.projection.BouquetList;

@Repository
public interface BouquetDao extends GenericDao<Bouquet, BouquetList> {

  Bouquet findByIdAndActiveTrue(Integer id);

  Slice<BouquetList> findAllByCategoriesId(Integer id, Pageable pageable);

  @Query(value = "SELECT b FROM Bouquet b JOIN b.categories c WHERE b.active = true AND c.active = true AND c.id = ?1")
  Slice<BouquetList> findAllByCategoriesIdAndActiveTrue(Integer id, Pageable pageable);
}