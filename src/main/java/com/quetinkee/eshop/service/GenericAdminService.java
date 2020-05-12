package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.GenericDao;
import com.quetinkee.eshop.model.AbstractEntity;
import com.quetinkee.eshop.model.projection.InterfaceList;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.utils.ValidationError;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

abstract public class GenericAdminService<S extends GenericDao, T extends AbstractEntity, K extends InterfaceList> {

  protected final S dao;

  private final Sort sort;
  private final Validator validator;

  public GenericAdminService(Validator validator, S dao, Sort sort) {
    this.validator = validator;
    this.dao = dao;
    this.sort = sort;
  }

  @Transactional
  abstract public T update(T original, T newData);

  @Transactional(readOnly = true)
  public T find(Integer id) {
    Optional<T> record = this.dao.findById(id);
    return record.isPresent() ? record.get() : null;
  }

  @Transactional(readOnly = true)
  public Slice<K> getSlice(Integer pageNum, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNum, pageSize, this.sort);
    return this.dao.findAllBy(paging);
  }

  @Transactional(readOnly = true)
  public List<OptionList> getList() {
    return this.dao.findAllBy(this.sort);
  }

  @Transactional
  public void delete (T record) {
    Objects.requireNonNull(record);
    this.dao.delete(record);
  }

  @Transactional
  public T create(T record) {
    Objects.requireNonNull(record);
    return (T) this.dao.save(record);
  }

  protected boolean validate(T record) throws ValidationError{
    Set<ConstraintViolation<T>> violations = validator.validate(record);
    if (!violations.isEmpty()) {
      String msg = violations.stream().map( n -> n.getMessage() ).collect( Collectors.joining("\n") );
      throw new ValidationError(msg);
    }
    return true;
  }
}
