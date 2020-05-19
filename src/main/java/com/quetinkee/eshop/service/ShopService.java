package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.dao.FilterDao;
import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.Bouquet_;
import com.quetinkee.eshop.model.Category_;
import com.quetinkee.eshop.model.Flower_;
import com.quetinkee.eshop.model.enums.Size;
import com.quetinkee.eshop.model.projection.CategoryList;
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
import com.quetinkee.eshop.model.projection.BouquetList;
import com.quetinkee.eshop.dao.BouquetDao;
import com.quetinkee.eshop.dao.ReviewDao;
import com.quetinkee.eshop.model.Review;
import com.quetinkee.eshop.model.Review_;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.utils.helpers.BouquetDetail;
import com.quetinkee.eshop.model.projection.ReviewList;
import com.quetinkee.eshop.rabbit.CacheRabbit;
import com.quetinkee.eshop.rabbit.SearchRabbit;
import com.quetinkee.eshop.utils.ValidationException;
import com.quetinkee.eshop.utils.helpers.ReviewSubmit;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShopService {

  private final BouquetDao bouquetDao;
  private final CategoryDao categoryDao;
  private final FilterDao filterDao;
  private final ReviewDao reviewDao;

  private final SearchRabbit searchRabbit;
  private final CacheRabbit cacheRabbit;

  @Autowired
  public ShopService(BouquetDao bouquetDao, CategoryDao categoryDao, FilterDao shopDao, ReviewDao reviewDao, SearchRabbit searchRabbit, CacheRabbit cacheRabbit) {
    this.bouquetDao = bouquetDao;
    this.categoryDao = categoryDao;
    this.filterDao = shopDao;
    this.reviewDao = reviewDao;

    this.searchRabbit = searchRabbit;
    this.cacheRabbit = cacheRabbit;
  }

  @Transactional(readOnly = true)
  public List<CategoryList> findCategories(boolean showAll) {
    if (showAll) return this.categoryDao.findShopBy(Sort.by(Category_.NAME));
    return this.categoryDao.findShopByActiveTrue();
  }

  @Transactional(readOnly = true)
  public FilterInfo getFilterInfo (Integer id, boolean showAll) {
    return new FilterInfo(
        this.findCategories(showAll),
        this.findFlowers(id, showAll),
        this.findColors(id, showAll),
        this.findSizes(id, showAll),
        this.findPrices(id, showAll)
      );
  }

  @Transactional(readOnly = true)
  public Bouquet findBouquet(Integer id, boolean showAll) {
    if (showAll) {
      Optional<Bouquet> bouquet = this.bouquetDao.findById(id);
      return bouquet.isPresent() ? bouquet.get() : null;
    }
    return this.bouquetDao.findByIdAndActiveTrue(id);
  }

  @Transactional(readOnly = true)
  public BouquetDetail findBouquetDetail(Integer id, boolean showAll) {
    Bouquet bouquet = this.findBouquet(id, showAll);
    if (bouquet != null) {
      return new BouquetDetail(bouquet, this.reviewDao.findAvgRating(bouquet), -1);
    }
    return null;
  }

  @Transactional(readOnly = true)
  public Slice<BouquetList> findBouquetsInCategory(Integer id, Integer pageNum, Integer pageSize, boolean showAll) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Bouquet_.NAME));
    if (showAll) return this.bouquetDao.findAllByCategoriesId(id, paging);
    return this.bouquetDao.findAllByCategoriesIdAndActiveTrue(id, paging);
  }

  private Set<Integer> findColors(Integer id, boolean showAll) {
    if (id == null) return this.filterDao.searchColors(showAll);
    return this.filterDao.searchColorsByCategoriesId(showAll, id);
  }

  private Set<OptionList> findFlowers(Integer id, boolean showAll) {
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

  @Transactional(readOnly = true)
  public Slice<BouquetList> getFilteredProducts(Integer id, FilterRequest request, Integer pageNum, Integer pageSize, boolean showAll) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Bouquet_.NAME));
    return this.filterDao.findResults(showAll, id, request, paging);
  }

  @Transactional(readOnly = true)
  public List<BouquetList> getSimilarProducts(Integer id, Integer pageSize, boolean showAll) {
    Pageable paging = PageRequest.of(0, pageSize, Sort.by(Bouquet_.ID));
    return this.filterDao.searchAllSimilar(showAll, id, paging);
  }

  public boolean isBouquetReview(Bouquet bouquet, User user) {
    return this.reviewDao.findByBouquetIdAndUserId(bouquet.getId(), user.getId()) != null;
  }

  @Transactional
  public void addBouquetReview(Bouquet bouquet, User user, ReviewSubmit rs) throws ValidationException {
    if (this.isBouquetReview(bouquet, user)) throw new ValidationException("Recenze je již uložená, děkujeme.");

    bouquet.addReview(new Review(user, rs.getMessage(),  rs.getRating()));
    this.bouquetDao.save(bouquet);
//    Review review = new Review(bouquet, user, rs.getMessage(),  rs.getRating());
//    this.reviewDao.save(review);
  }

  @Transactional(readOnly = true)
  public Slice<ReviewList> getBouquetReviews(Bouquet bouquet, Integer pageNum, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Review_.CREATED));
    return this.reviewDao.findAllByBouquet(bouquet, paging);
  }

  public Slice<BouquetList> getSeachResults(String find, Integer pageNum, Integer pageSize, boolean showAll) throws ResponseStatusException {
    List<Integer> keys = this.searchRabbit.find(find);
    if (keys == null) throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Služba dočasně nedostupná");

    if (keys.size() > 0) {
      String order = keys.toString();
      order = ',' + order.substring(1, order.length()-1) + ',';
      Pageable paging = PageRequest.of(0, pageSize);
      return this.bouquetDao.findAllByActiveAndIdInAndCategoriesNotNull(showAll, keys, order, paging);
    }
    return null;
  }
}
