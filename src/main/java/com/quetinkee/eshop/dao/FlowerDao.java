package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.projection.FlowerList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerDao extends JpaRepository<Flower, Integer> {

  Slice<Flower> findAllBy(Pageable pageable);

  List<FlowerList> findAllBy(Sort sort);
}
