package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.projection.BouquetFlowerCountList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.quetinkee.eshop.model.projection.BouquetList;
import com.quetinkee.eshop.model.projection.BouquetListFixForConverter;
import java.util.List;
import java.util.Set;

@Repository
public interface BouquetDao extends GenericDao<Bouquet, BouquetList> {

  Bouquet findByIdAndActiveTrue(Integer id);

  Slice<BouquetList> findAllByCategoriesId(Integer id, Pageable pageable);

  @Query(nativeQuery = true, value = "SELECT DISTINCT b.*,POSITION(CONCAT(',',b.id,',') IN ?4) AS ord_pos FROM Bouquet b JOIN Bouquet_categories bc ON (b.id = bc.bouquets_id) JOIN Category c ON (c.id = bc.categories_id)" +
          " WHERE (?1 = true OR b.active = true) AND (?1 = true OR c.active = true) AND (?2 = 0 OR c.id = ?2) AND b.id IN ?3 ORDER BY ord_pos")
  Slice<BouquetListFixForConverter> findAllByActiveAndIdInAndCategoriesId(Boolean showAll, Integer id, List<Integer> ids, String order, Pageable pageable);

  @Query(value = "SELECT b FROM Bouquet b JOIN b.categories c WHERE b.active = true AND c.active = true AND c.id = ?1")
  Slice<BouquetList> findAllByCategoriesIdAndActiveTrue(Integer id, Pageable pageable);

  @Query(nativeQuery = true, value = "SELECT bouquet_id AS bouquetId, flower_id AS flowerId, b.count AS count FROM bouquet_flower_count b WHERE bouquet_id IN ?1")
  Set<BouquetFlowerCountList> findCountsAllByIdIn(Set<Integer> ids);
}