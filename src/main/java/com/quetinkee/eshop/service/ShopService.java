package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.BoquetDao;
import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.projection.FlowerList;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import com.quetinkee.eshop.utils.PanelInfo;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopService {

  private final BoquetDao boquetDao;
  private final CategoryDao categoryDao;
  private final FlowerDao flowerDao;

  @Autowired
  public ShopService(BoquetDao boquetDao, CategoryDao categoryDao, FlowerDao flowerDao) {
    this.boquetDao = boquetDao;
    this.categoryDao = categoryDao;
    this.flowerDao = flowerDao;
  }

  @Transactional(readOnly = true)
  public Set<CategoryList> findCategories(boolean showAll) {
    if (showAll) return this.categoryDao.findAllBy();
    return this.categoryDao.findAllByActiveTrue();
  }

  @Transactional(readOnly = true)
  public PanelInfo getSearchInfo (Integer id, boolean showAll) {
    return new PanelInfo(
        this.findCategories(showAll),
        this.findFlowers(id, showAll),
        this.findColors(id, showAll),
        this.findSizes(id, showAll),
        this.findPrices(id, showAll)
      );
  }

  @Transactional(readOnly = true)
  public Boquet findBoquet(Integer id, boolean showAll) {
    if (showAll) {
      Optional<Boquet> boquet = this.boquetDao.findById(id);
      return boquet.isPresent() ? boquet.get() : null;
    }
    return this.boquetDao.findByIdAndActiveTrue(id);
  }

  @Transactional(readOnly = true)
  public Slice<BoquetList> findBoquetsInCategory(Integer id, Integer pageNum, Integer pageSize, boolean showAll) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("name"));
    if (showAll) return this.boquetDao.findAllByCategoriesId(id, paging);
    return this.boquetDao.findAllByCategoriesIdAndActiveTrue(id, paging);
  }

  private Set<Integer> findColors(Integer id, boolean showAll) {
    if (id == null) return this.boquetDao.searchColors(showAll);
    return this.boquetDao.searchColorsByCategoriesId(showAll, id);
  }

  private Set<FlowerList> findFlowers(Integer id, boolean showAll) {
    if (id == null) return this.flowerDao.searchFlowers(showAll);
    return this.flowerDao.searchFlowersByCategoriesId(showAll, id);
  }

  private Set<Size> findSizes(Integer id, boolean showAll) {
    if (id == null) return this.boquetDao.searchSizes(showAll);
    return this.boquetDao.searchSizesyCategoriesId(showAll, id);
  }

  private MinMaxPrice findPrices(Integer id, boolean showAll) {
    if (id == null) return this.boquetDao.findFirstPrices(showAll);
    return this.boquetDao.findFirstPricesByCategoriesId(showAll, id);
  }
}
