package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.projection.CategoryList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends GenericDao<Category, CategoryList> {

  Category findByIdAndActiveTrue(Integer id);

  List<CategoryList> findShopBy(Sort sort);

  List<CategoryList> findShopByActiveTrue();

  Slice<CategoryList> findAllByActiveTrue(Pageable pageable);
}