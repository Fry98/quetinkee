package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.model.Flower;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowerService {

  private final FlowerDao dao;

  public FlowerService(FlowerDao dao) {
    this.dao = dao;
  }

  @Transactional(readOnly = true)
  public Flower find(Integer id) {
    Optional<Flower> flower = this.dao.findById(id);
    return flower.isPresent() ? flower.get() : null;
  }

  @Transactional
  public void update(Flower flower) {
    Objects.requireNonNull(flower);
    this.dao.save(flower);
  }

  @Transactional
  public void delete (Flower flower) {
    Objects.requireNonNull(flower);
    this.dao.delete(flower);
  }

  @Transactional
  public void persist(Flower flower) {
    Objects.requireNonNull(flower);
    this.dao.save(flower);
  }
}
