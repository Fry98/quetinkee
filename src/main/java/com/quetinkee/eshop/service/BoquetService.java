package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.BoquetDao;
import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.BoquetFlowerCount;
import com.quetinkee.eshop.model.Boquet_;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Color;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.utils.UploadImage;
import com.quetinkee.eshop.utils.edit.BoquetEdit;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoquetService {

  private final BoquetDao boquetDao;
  private final FlowerDao flowerDao;
  private final CategoryDao categoryDao;
  private final UploadImage uploader;

  @Autowired
  public BoquetService(BoquetDao boquetDao, FlowerDao flowerDao, CategoryDao categoryDao, UploadImage uploader) {
    this.boquetDao = boquetDao;
    this.flowerDao = flowerDao;
    this.categoryDao = categoryDao;
    this.uploader = uploader;
  }

  public Boquet find(Integer id) {
    Optional<Boquet> boquet = this.boquetDao.findById(id);
    return boquet.isPresent() ? boquet.get() : null;
  }

  @Transactional(readOnly = true)
  public Slice<BoquetList> findAll(Integer pageNum, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Boquet_.NAME));
    return this.boquetDao.findAllBy(paging);
  }

  @Transactional(readOnly = true)
  public List<BoquetList> getList() {
    return this.boquetDao.findAllBy(Sort.by(Boquet_.NAME));
  }

  @Transactional
  public void update(Boquet original, BoquetEdit newData) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(newData);
    Boquet boquet = newData.getBoquet();

    if (boquet.getName() != null) original.setName(boquet.getName());
    if (boquet.getPerex() != null) original.setPerex(boquet.getPerex());
    if (boquet.getDescription() != null) original.setDescription(boquet.getDescription());
    if (boquet.getPrice() != null) original.setPrice(boquet.getPrice());
    if (boquet.getSize() != null) original.setSize(boquet.getSize());
    if (boquet.isActive() != null) original.setActive(boquet.isActive());

    if (newData.getKeyColors() != null) this.updateColors(original, newData.getKeyColors());
    if (newData.getKeyCategories() != null) this.updateCategories(original, newData.getKeyCategories());
    if (newData.getKeyFlowerCount() != null) this.updateFlowerCounts(original, newData.getKeyFlowerCount());
    this.boquetDao.save(original);
  }

  @Transactional
  public void delete (Boquet boquet) {
    Objects.requireNonNull(boquet);
    this.boquetDao.delete(boquet);
  }

  @Transactional
  public void persist(Boquet boquet) {
    Objects.requireNonNull(boquet);
    this.boquetDao.save(boquet);
  }

  @Transactional
  public void persist(BoquetEdit newData, MultipartFile file) {
    Objects.requireNonNull(newData);

    Boquet boquet = newData.getBoquet();
    if (newData.getKeyColors() != null) this.updateColors(boquet, newData.getKeyColors());
    if (newData.getKeyCategories() != null) this.updateCategories(boquet, newData.getKeyCategories());
    if (newData.getKeyFlowerCount() != null) this.updateFlowerCounts(boquet, newData.getKeyFlowerCount());

    if (file != null && !file.isEmpty()) {
      if (boquet.getImage() != null) this.uploader.remove(boquet.getImage());
      if (boquet.getId() == null) this.boquetDao.save(boquet);
      boquet.setImage(this.uploader.store(boquet.getId(), file));
    }

    this.boquetDao.save(boquet);
  }

  private void updateColors(Boquet boquet, List<Integer> keyColors) {
    // remove
    if (boquet.getColors() != null) {
      Iterator<Color> it = boquet.getColors().iterator();
      while (it.hasNext()) {
        if (!keyColors.contains(it.next().getValue())) it.remove();
      }
    }
    // add
    keyColors.forEach( colorId -> {
      boquet.addColor(Color.valueOf(colorId));
    });
  }

  private void updateCategories(Boquet boquet, List<Integer> keyCategories) {
    // remove
    if (boquet.getCategories() != null) {
      Iterator<Category> it = boquet.getCategories().iterator();
      while (it.hasNext()) {
        if (!keyCategories.contains(it.next().getId())) it.remove();
      }
    }
    // add
    keyCategories.forEach( categoryId -> {
      Optional<Category> category = this.categoryDao.findById(categoryId);
      if (category.isPresent()) boquet.addCategory(category.get());
    });
  }

  private void updateFlowerCounts(Boquet boquet, Map<Integer,Integer> keyCounts) {
    // remove
    if (boquet.getBoquetflowerCount() != null) {
      Iterator<BoquetFlowerCount> it = boquet.getBoquetflowerCount().iterator();
      while (it.hasNext()) {
        if (!keyCounts.containsKey(it.next().getFlower().getId())) it.remove();
      }
    }
    // add
    keyCounts.forEach( (flowerId, count) -> {
      Optional<Flower> flower = this.flowerDao.findById(flowerId);
      if (flower.isPresent()) boquet.addBoquetFlowerCount( new BoquetFlowerCount( flower.get(), count) );
    });
  }
}
