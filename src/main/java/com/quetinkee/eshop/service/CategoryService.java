package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Category_;
import com.quetinkee.eshop.model.projection.CategoryList;
import java.util.Objects;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService extends GenericAdminService<CategoryDao, Category, CategoryList> {

  @Autowired
  public CategoryService(Validator validator, CategoryDao dao) {
    super(validator, dao,Sort.by(Category_.PRIORITY, Category_.NAME));
  }

  @Transactional
  @Override
  public Category update(Category original, Category newData) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(newData);

    if (newData.getName() != null) original.setName(newData.getName());
    if (newData.getPriority() != null) original.setPriority(newData.getPriority());
    if (newData.isActive() != null) original.setActive(newData.isActive());

    if (this.validate(original)) return this.dao.save(original);
    return null;
  }
}
