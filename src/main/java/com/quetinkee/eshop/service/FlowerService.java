package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.Flower_;
import com.quetinkee.eshop.model.projection.FlowerList;
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
public class FlowerService {

  private final FlowerDao dao;

  @Autowired
  public FlowerService(FlowerDao dao) {
    this.dao = dao;
  }

  @Transactional(readOnly = true)
  public Flower find(Integer id) {
    Optional<Flower> flower = this.dao.findById(id);
    return flower.isPresent() ? flower.get() : null;
  }

  @Transactional(readOnly = true)
  public Slice<Flower> findAll(Integer pageNum, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Flower_.NAME));
    return this.dao.findAllBy(paging);
  }

  @Transactional(readOnly = true)
  public List<FlowerList> getList() {
    return this.dao.findAllBy(Sort.by(Flower_.NAME));
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
