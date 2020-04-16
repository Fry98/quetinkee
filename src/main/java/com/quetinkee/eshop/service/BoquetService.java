package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.BoquetDao;
import com.quetinkee.eshop.model.Boquet;
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
public class BoquetService {

  private final BoquetDao dao;

  @Autowired
  public BoquetService(BoquetDao dao) {
    this.dao = dao;
  }

  public Boquet find(Integer id) {
    Optional<Boquet> boquet = this.dao.findById(id);
    return boquet.isPresent() ? boquet.get() : null;
  }

  @Transactional(readOnly = true)
  public Boquet find(Integer id, boolean activeOnly) {
    if (activeOnly) return this.dao.findByIdAndActiveTrue(id);
    return this.find(id);
  }

  @Transactional(readOnly = true)
  public Slice<Boquet> findAll(Integer pageNum, Integer pageSize, boolean active) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("name"));
    if (active) return this.dao.findAllByActiveTrue(paging);
    return this.dao.findAllBy(paging);
  }

  @Transactional(readOnly = true)
  public Slice<Boquet> findAllInCategory(Integer id, Integer pageNum, Integer pageSize, boolean active) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("name"));
    if (active) return this.dao.findAllByCategoriesId(id, paging);
    return this.dao.findAllByCategoriesIdAndActiveTrue(id, paging);
  }

  @Transactional
  public void update(Boquet boquet) {
    Objects.requireNonNull(boquet);
    this.dao.save(boquet);
  }

  @Transactional
  public void delete (Boquet boquet) {
    Objects.requireNonNull(boquet);
    this.dao.delete(boquet);
  }

  @Transactional
  public void persist(Boquet boquet) {
    Objects.requireNonNull(boquet);
    this.dao.save(boquet);
  }
}
