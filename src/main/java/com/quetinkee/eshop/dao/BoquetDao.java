package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.projection.BoquetList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoquetDao extends JpaRepository<Boquet, Integer> {

  Boquet findByIdAndActiveTrue(Integer id);

  Slice<BoquetList> findAllBy(Pageable pageable);

  List<BoquetList> findAllBy(Sort sort);

  Slice<BoquetList> findAllByCategoriesId(Integer id, Pageable pageable);

  @Query(value = "SELECT b FROM Boquet b JOIN b.categories c WHERE b.active = true AND c.active = true AND c.id = ?1")
  Slice<BoquetList> findAllByCategoriesIdAndActiveTrue(Integer id, Pageable pageable);
}
