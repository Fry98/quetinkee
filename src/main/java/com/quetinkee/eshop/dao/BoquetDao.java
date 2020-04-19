package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoquetDao extends JpaRepository<Boquet, Integer> {

  static String ACTIVE = "WHERE (?1 = true OR b.active = true)";
  static String ACTIVE_CATEGORY = "JOIN b.categories c WHERE (?1 = true OR b.active = true) AND c.id = ?2";

  Boquet findByIdAndActiveTrue(Integer id);

  Slice<Boquet> findAllBy(Pageable pageable);

  Slice<Boquet> findAllByActiveTrue(Pageable pageable);

  Slice<BoquetList> findAllByCategoriesId(Integer id, Pageable pageable);

  Slice<BoquetList> findAllByCategoriesIdAndActiveTrue(Integer id, Pageable pageable);

  @Query(value = "SELECT b.size FROM Boquet b " + ACTIVE + " GROUP BY b.size")
  Set<Size> searchSizes(Boolean showAll);

  @Query(value = "SELECT b.size FROM Boquet b " + ACTIVE_CATEGORY + " GROUP BY b.size")
  Set<Size> searchSizesyCategoriesId(Boolean showAll, Integer id);

  @Query(value = "SELECT clr FROM Boquet b JOIN b.colors clr " + ACTIVE + " GROUP BY clr")
  Set<Integer> searchColors(Boolean showAll);

  @Query(value = "SELECT clr FROM Boquet b JOIN b.colors clr " + ACTIVE_CATEGORY + " GROUP BY clr")
  Set<Integer> searchColorsByCategoriesId(Boolean showAll, Integer id);

  @Query(value = "SELECT MIN(b.price) AS min, MAX(b.price) AS max FROM Boquet b " + ACTIVE)
  MinMaxPrice findFirstPrices(Boolean showAll);

  @Query(value = "SELECT MIN(b.price) AS min, MAX(b.price) AS max FROM Boquet b " + ACTIVE_CATEGORY)
  MinMaxPrice findFirstPricesByCategoriesId(Boolean showAll, Integer id);
}