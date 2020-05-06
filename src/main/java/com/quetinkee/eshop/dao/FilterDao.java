package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.projection.FlowerList;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface FilterDao extends JpaRepository<Boquet, Integer>,FilterDynamic {

  static String ACTIVE = "JOIN b.categories c WHERE (?1 = true OR b.active = true) AND (?1 = true OR c.active = true)";

  @Query(value = "SELECT b.size FROM Boquet b " + ACTIVE + " GROUP BY b.size")
  Set<Size> searchSizes(Boolean showAll);

  @Query(value = "SELECT b.size FROM Boquet b " + ACTIVE + " AND c.id = ?2 GROUP BY b.size")
  Set<Size> searchSizesyCategoriesId(Boolean showAll, Integer id);

  @Query(value = "SELECT clr FROM Boquet b JOIN b.colors clr " + ACTIVE + " GROUP BY clr")
  Set<Integer> searchColors(Boolean showAll);

  @Query(value = "SELECT clr FROM Boquet b JOIN b.colors clr " + ACTIVE + " AND c.id = ?2 GROUP BY clr")
  Set<Integer> searchColorsByCategoriesId(Boolean showAll, Integer id);

  @Query(value = "SELECT MIN(b.price) AS min, MAX(b.price) AS max FROM Boquet b " + ACTIVE)
  MinMaxPrice findFirstPrices(Boolean showAll);

  @Query(value = "SELECT MIN(b.price) AS min, MAX(b.price) AS max FROM Boquet b " + ACTIVE + " AND c.id = ?2")
  MinMaxPrice findFirstPricesByCategoriesId(Boolean showAll, Integer id);

  @Query(value = "SELECT f FROM Flower f JOIN f.boquetflowerCount fc JOIN fc.boquet b " + ACTIVE + " GROUP BY f")
  Set<FlowerList> searchFlowers(Boolean showAll, Sort sort);

  @Query(value = "SELECT f FROM Flower f JOIN f.boquetflowerCount fc JOIN fc.boquet b " + ACTIVE + " AND c.id = ?2 GROUP BY f")
  Set<FlowerList> searchFlowersByCategoriesId(Boolean showAll, Integer id, Sort sort);
}
