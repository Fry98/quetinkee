package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Boquet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoquetDao extends JpaRepository<Boquet, Integer> {

  Boquet findByIdAndActiveTrue(Integer id);

  Slice<Boquet> findAllBy(Pageable pageable);

  Slice<Boquet> findAllByActiveTrue(Pageable pageable);

  Slice<Boquet> findAllByCategoriesId(Integer id, Pageable pageable);

  Slice<Boquet> findAllByCategoriesIdAndActiveTrue(Integer id, Pageable pageable);
}
