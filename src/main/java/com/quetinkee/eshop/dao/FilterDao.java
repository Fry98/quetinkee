package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.projection.BouquetList;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import com.quetinkee.eshop.model.projection.OptionList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface FilterDao extends JpaRepository<Bouquet, Integer>,FilterDynamic {

  static String ACTIVE = "JOIN b.categories c WHERE (?1 = true OR b.active = true) AND (?1 = true OR c.active = true)";

  @Query(value = "SELECT b.size FROM Bouquet b " + ACTIVE + " GROUP BY b.size")
  Set<Size> searchSizes(Boolean showAll);

  @Query(value = "SELECT b.size FROM Bouquet b " + ACTIVE + " AND c.id = ?2 GROUP BY b.size")
  Set<Size> searchSizesyCategoriesId(Boolean showAll, Integer id);

  @Query(value = "SELECT clr FROM Bouquet b JOIN b.colors clr " + ACTIVE + " GROUP BY clr")
  Set<Integer> searchColors(Boolean showAll);

  @Query(value = "SELECT clr FROM Bouquet b JOIN b.colors clr " + ACTIVE + " AND c.id = ?2 GROUP BY clr")
  Set<Integer> searchColorsByCategoriesId(Boolean showAll, Integer id);

  @Query(value = "SELECT MIN(b.price) AS min, MAX(b.price) AS max FROM Bouquet b " + ACTIVE)
  MinMaxPrice findFirstPrices(Boolean showAll);

  @Query(value = "SELECT MIN(b.price) AS min, MAX(b.price) AS max FROM Bouquet b " + ACTIVE + " AND c.id = ?2")
  MinMaxPrice findFirstPricesByCategoriesId(Boolean showAll, Integer id);

  @Query(value = "SELECT f FROM Flower f JOIN f.bouquetFlowerCount fc JOIN fc.bouquet b " + ACTIVE + " GROUP BY f")
  Set<OptionList> searchFlowers(Boolean showAll, Sort sort);

  @Query(value = "SELECT f FROM Flower f JOIN f.bouquetFlowerCount fc JOIN fc.bouquet b " + ACTIVE + " AND c.id = ?2 GROUP BY f")
  Set<OptionList> searchFlowersByCategoriesId(Boolean showAll, Integer id, Sort sort);

  @Query(value = "SELECT DISTINCT b FROM Bouquet b JOIN b.categories c LEFT JOIN b.bouquetFlowerCount bof LEFT JOIN b.colors boc" +
                  " LEFT JOIN Bouquet oo ON (b.id != oo.id) LEFT JOIN oo.bouquetFlowerCount oof LEFT JOIN oo.colors ooc" +
                  " WHERE ((bof.flower = oof.flower AND bof.flower != NULL) OR (boc = ooc AND boc != NULL)) AND oo.id =?2 AND b.id != ?2" +
                  " AND (?1 = true OR b.active = true) AND (?1 = true OR c.active = true)")
  List<BouquetList> searchAllSimilar(Boolean showAll, Integer id, Pageable pageable);
}
