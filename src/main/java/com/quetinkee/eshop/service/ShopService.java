package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.BoquetDao;
import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.dao.FilterDao;
import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.Boquet_;
import com.quetinkee.eshop.model.Category_;
import com.quetinkee.eshop.model.Flower_;
import com.quetinkee.eshop.model.Size;
import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.model.projection.CategoryList;
import com.quetinkee.eshop.model.projection.FlowerList;
import com.quetinkee.eshop.model.projection.MinMaxPrice;
import com.quetinkee.eshop.utils.FilterInfo;
import com.quetinkee.eshop.utils.FilterRequest;
import java.util.List;
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
  private final FilterDao filterDao;

  @Autowired
  public ShopService(BoquetDao boquetDao, CategoryDao categoryDao, FilterDao shopDao) {
    this.boquetDao = boquetDao;
    this.categoryDao = categoryDao;
    this.filterDao = shopDao;
  }

  @Transactional(readOnly = true)
  public List<CategoryList> findCategories(boolean showAll) {
    if (showAll) return this.categoryDao.findAllBy(Sort.by(Category_.NAME));
    return this.categoryDao.findAllByActiveTrue();
  }

  @Transactional(readOnly = true)
  public FilterInfo getSearchInfo (Integer id, boolean showAll) {
    return new FilterInfo(
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
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Boquet_.NAME));
    if (showAll) return this.boquetDao.findAllByCategoriesId(id, paging);
    return this.boquetDao.findAllByCategoriesIdAndActiveTrue(id, paging);
  }

  private Set<Integer> findColors(Integer id, boolean showAll) {
    if (id == null) return this.filterDao.searchColors(showAll);
    return this.filterDao.searchColorsByCategoriesId(showAll, id);
  }

  private Set<FlowerList> findFlowers(Integer id, boolean showAll) {
    if (id == null) return this.filterDao.searchFlowers(showAll, Sort.by(Flower_.NAME));
    return this.filterDao.searchFlowersByCategoriesId(showAll, id, Sort.by(Flower_.NAME));
  }

  private Set<Size> findSizes(Integer id, boolean showAll) {
    if (id == null) return this.filterDao.searchSizes(showAll);
    return this.filterDao.searchSizesyCategoriesId(showAll, id);
  }

  private MinMaxPrice findPrices(Integer id, boolean showAll) {
    if (id == null) return this.filterDao.findFirstPrices(showAll);
    return this.filterDao.findFirstPricesByCategoriesId(showAll, id);
  }

  public Slice<BoquetList> getFilteredProducts(Integer id, FilterRequest request, Integer pageNum, Integer pageSize, boolean showAll) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Boquet_.NAME));
    return this.filterDao.findResults(showAll, id, request, paging);

  }
}
