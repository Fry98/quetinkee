package com.quetinkee.eshop.dao;

import static com.quetinkee.eshop.dao.BoquetDao.ACTIVE;
import static com.quetinkee.eshop.dao.BoquetDao.ACTIVE_CATEGORY;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.projection.FlowerList;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerDao extends JpaRepository<Flower, Integer> {

  Slice<Flower> findAllBy(Pageable pageable);

  @Query(value = "SELECT f FROM Flower f, BoquetFlowerCount fc, Boquet b " + ACTIVE + " GROUP BY f")
  Set<FlowerList> searchFlowers(Boolean showAll);

  @Query(value = "SELECT f FROM Flower f, BoquetFlowerCount fc, Boquet b, Category c " + ACTIVE_CATEGORY + " GROUP BY f")
  Set<FlowerList> searchFlowersByCategoriesId(Boolean showAll, Integer id);
}
