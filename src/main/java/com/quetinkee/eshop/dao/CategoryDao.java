package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Category;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {

  Category findByIdAndActiveTrue(Integer id);

  Slice<Category> findAllBy(Pageable pageable);

  Slice<Category> findAllByActiveTrue(Pageable pageable);

  List<Category> findAllByActiveTrue();
}