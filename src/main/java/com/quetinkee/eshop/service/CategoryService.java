package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.model.Category;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  private final CategoryDao dao;

  public CategoryService(CategoryDao dao) {
    this.dao = dao;
  }

  @Transactional(readOnly = true)
  public Category find(Integer id) {
    Optional<Category> category = this.dao.findById(id);
    return category.isPresent() ? category.get() : null;
  }

  @Transactional
  public void update(Category category) {
    Objects.requireNonNull(category);
    this.dao.save(category);
  }

  @Transactional
  public void delete (Category category) {
    Objects.requireNonNull(category);
    this.dao.delete(category);
  }

  @Transactional
  public void persist(Category category) {
    Objects.requireNonNull(category);
    this.dao.save(category);
  }
}
