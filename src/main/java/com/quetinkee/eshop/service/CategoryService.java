package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.model.Category;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  private final CategoryDao dao;

  @Autowired
  public CategoryService(CategoryDao dao) {
    this.dao = dao;
  }

  @Transactional(readOnly = true)
  public Category find(Integer id) {
    Optional<Category> category = this.dao.findById(id);
    return category.isPresent() ? category.get() : null;
  }

  @Transactional(readOnly = true)
  public Category find(Integer id, boolean activeOnly) {
    if (activeOnly) return this.dao.findByIdAndActiveTrue(id);
    return this.find(id);
  }

  @Transactional(readOnly = true)
  public List<Category> findAll(boolean active) {
    if (active) return this.dao.findAllByActiveTrue();
    return this.dao.findAll();
  }

  @Transactional(readOnly = true)
  public Slice<Category> findAll(Integer pageNum, Integer pageSize, boolean active) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("name"));
    if (active) return this.dao.findAllByActiveTrue(paging);
    return this.dao.findAllBy(paging);
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
