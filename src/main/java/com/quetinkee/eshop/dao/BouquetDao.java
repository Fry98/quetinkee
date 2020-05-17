package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Bouquet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.quetinkee.eshop.model.projection.BouquetList;
import java.util.List;

@Repository
public interface BouquetDao extends GenericDao<Bouquet, BouquetList> {

  Bouquet findByIdAndActiveTrue(Integer id);

  Slice<BouquetList> findAllByCategoriesId(Integer id, Pageable pageable);

  @Query(nativeQuery = true, value = "SELECT DISTINCT b.*,POSITION(CONCAT(',',b.id,',') IN ?3) AS ord_pos FROM Bouquet b JOIN Bouquet_categories bc ON (b.id = bc.bouquets_id) JOIN Category c ON (c.id = bc.categories_id)" +
          " WHERE (?1 = true OR b.active = true) AND (?1 = true OR c.active = true) AND b.id IN ?2 ORDER BY ord_pos")
  Slice<BouquetList> findAllByActiveAndIdInAndCategoriesNotNull(Boolean showAll, List<Integer> ids, String order, Pageable pageable);

  @Query(value = "SELECT b FROM Bouquet b JOIN b.categories c WHERE b.active = true AND c.active = true AND c.id = ?1")
  Slice<BouquetList> findAllByCategoriesIdAndActiveTrue(Integer id, Pageable pageable);
}