package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.BoquetDao;
import com.quetinkee.eshop.model.Boquet;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoquetService {

  private final BoquetDao dao;

  public BoquetService(BoquetDao dao) {
    this.dao = dao;
  }

  @Transactional(readOnly = true)
  public Boquet find(Integer id) {
    Optional<Boquet> boquet = this.dao.findById(id);
    return boquet.isPresent() ? boquet.get() : null;
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
