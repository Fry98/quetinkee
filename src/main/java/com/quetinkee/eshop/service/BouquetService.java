package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.BouquetFlowerCount;
import com.quetinkee.eshop.model.Bouquet_;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.enums.Color;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.utils.UploadImage;
import com.quetinkee.eshop.utils.helpers.BouquetEdit;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.quetinkee.eshop.model.projection.BouquetList;
import com.quetinkee.eshop.dao.BouquetDao;
import com.quetinkee.eshop.rabbit.CacheRabbit;
import com.quetinkee.eshop.rabbit.SearchRabbit;
import com.quetinkee.eshop.utils.ValidationException;
import com.quetinkee.eshop.utils.helpers.BouquetDetail;
import javax.validation.Validator;

@Service
public class BouquetService extends GenericAdminService<BouquetDao, Bouquet, BouquetList>{

  private final FlowerDao flowerDao;
  private final CategoryDao categoryDao;
  private final UploadImage uploader;

  private final SearchRabbit searchRabbit;
  private final CacheRabbit cacheRabbit;

  @Autowired
  public BouquetService(Validator validator, BouquetDao dao, FlowerDao flowerDao, CategoryDao categoryDao, UploadImage uploader, SearchRabbit searchRabbit, CacheRabbit cacheRabbit) {
    super(validator, dao, Sort.by(Bouquet_.NAME));

    this.flowerDao = flowerDao;
    this.categoryDao = categoryDao;
    this.uploader = uploader;

    this.searchRabbit = searchRabbit;
    this.cacheRabbit = cacheRabbit;
  }

  @Transactional
  @Override
  public Bouquet update(Bouquet original, Bouquet newData) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(newData);

    if (newData.getName() != null) original.setName(newData.getName());
    if (newData.getPerex() != null) original.setPerex(newData.getPerex());
    if (newData.getDescription() != null) original.setDescription(newData.getDescription());
    if (newData.getPrice() != null) original.setPrice(newData.getPrice());
    if (newData.getSize() != null) original.setSize(newData.getSize());
    if (newData.isActive() != null) original.setActive(newData.isActive());

    if (this.validate(original)) return this.save(original);
    return null;
  }

  @Transactional
  @Override
  public Bouquet create(Bouquet record) {
    return this.save(record);
  }

  @Transactional
  public Bouquet update(Bouquet original, BouquetEdit newData, MultipartFile file) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(newData);

    if (newData.getKeyColors() != null) this.updateColors(original, newData.getKeyColors());
    if (newData.getKeyCategories() != null) this.updateCategories(original, newData.getKeyCategories());
    if (newData.getKeyFlowerCount() != null) this.updateFlowerCounts(original, newData.getKeyFlowerCount());

    if (file != null) this.updateFile(original, file);

    return this.update(original, newData.getBouquet());
  }

  @Transactional
  public Bouquet create(BouquetEdit newData, MultipartFile file) {
    Objects.requireNonNull(newData);

    Bouquet bouquet = newData.getBouquet();
    if (newData.getKeyColors() != null) this.updateColors(bouquet, newData.getKeyColors());
    if (newData.getKeyCategories() != null) this.updateCategories(bouquet, newData.getKeyCategories());
    if (newData.getKeyFlowerCount() != null) this.updateFlowerCounts(bouquet, newData.getKeyFlowerCount());

    if (file != null) this.updateFile(bouquet, file);

    return this.save(bouquet);
  }

  @Transactional
  @Override
  public void delete (Bouquet record) {
    this.searchRabbit.delete(record.getId());
    this.cacheRabbit.delete(BouquetDetail.class, record.getId());
    super.delete(record);
    if (record.getImage() != null) this.uploader.remove(record.getPath(), record.getImage());
  }

  private Bouquet save(Bouquet bouquet) {
    bouquet = this.dao.save(bouquet);
    this.searchRabbit.save(bouquet);
    return bouquet;
  }

  private void updateFile (Bouquet bouquet, MultipartFile file) {
    Objects.requireNonNull(bouquet);
    Objects.requireNonNull(file);

    if (!file.isEmpty()) {
      if (bouquet.getImage() != null) this.uploader.remove(bouquet.getPath(), bouquet.getImage());
      if (bouquet.getId() == null) this.dao.save(bouquet);    // only on create
      bouquet.setImage(this.uploader.store(bouquet.getId(), file));
      bouquet.setPath(this.uploader.getPath());
    }
  }

  private void updateColors(Bouquet bouquet, List<Integer> keyColors) {
    // remove
    if (bouquet.getColors() != null) {
      Iterator<Color> it = bouquet.getColors().iterator();
      while (it.hasNext()) {
        if (!keyColors.contains(it.next().getValue())) it.remove();
      }
    }
    // add
    keyColors.forEach( colorId -> {
      Color newColor = Color.typeOf(colorId);
      if (newColor != null) bouquet.addColor(newColor);
      else throw new ValidationException("Neexistující barva");
    });
  }

  private void updateCategories(Bouquet bouquet, List<Integer> keyCategories) {
    // remove
    if (bouquet.getCategories() != null) {
      Iterator<Category> it = bouquet.getCategories().iterator();
      while (it.hasNext()) {
        if (!keyCategories.contains(it.next().getId())) it.remove();
      }
    }
    // add
    keyCategories.forEach( categoryId -> {
      Optional<Category> category = this.categoryDao.findById(categoryId);
      if (category.isPresent()) bouquet.addCategory(category.get());
      else throw new ValidationException("Neexistující kategorie");
    });
  }

  private void updateFlowerCounts(Bouquet bouquet, Map<Integer,Integer> keyCounts) {
    // remove
    if (bouquet.getBouquetFlowerCount() != null) {
      Iterator<BouquetFlowerCount> it = bouquet.getBouquetFlowerCount().iterator();
      while (it.hasNext()) {
        if (!keyCounts.containsKey(it.next().getFlower().getId())) it.remove();
      }
    }
    // add
    keyCounts.forEach((flowerId, count) -> {
      Optional<Flower> flower = this.flowerDao.findById(flowerId);
      if (flower.isPresent()) bouquet.addBouquetFlowerCount(new BouquetFlowerCount( flower.get(), count) );
      else throw new ValidationException("Neexistující květina");
    });
  }
}
