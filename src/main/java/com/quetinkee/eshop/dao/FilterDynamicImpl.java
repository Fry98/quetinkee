package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.BouquetFlowerCount_;
import com.quetinkee.eshop.model.Bouquet_;
import com.quetinkee.eshop.model.Category_;
import com.quetinkee.eshop.model.Flower_;
import com.quetinkee.eshop.model.projection.BouquetListImpl;
import com.quetinkee.eshop.utils.FilterRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.quetinkee.eshop.model.projection.BouquetList;

/**
 * Because specification API is useless
 * @author davee
 */
@Repository
@Transactional(readOnly = true)
public class FilterDynamicImpl implements FilterDynamic {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Slice<BouquetList> findResults(boolean showAll, Integer id, FilterRequest request, Pageable pageable) {
    CriteriaBuilder builder = this.em.getCriteriaBuilder();
    CriteriaQuery<BouquetListImpl> query = builder.createQuery(BouquetListImpl.class);
    Root<Bouquet> root = query.from(Bouquet.class);
    CompoundSelection<BouquetListImpl> projection = builder.construct(BouquetListImpl.class, root.get(Bouquet_.id), root.get(Bouquet_.name), root.get(Bouquet_.image), root.get(Bouquet_.price), root.get(Bouquet_.size), root.get(Bouquet_.active));

    // set predicates
    Set<Predicate> predicates = this.getPredicates(builder, root, showAll, id, request);

    // query
    List<BouquetListImpl> content = this.em.createQuery(query.where(predicates.toArray(new Predicate[predicates.size()])).distinct(true).orderBy(builder.asc(root.get(Bouquet_.name))).select(projection))
            .setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize() + 1).getResultList();

    // find if there is more items
    boolean isNext = content.size() > pageable.getPageSize();
    if (isNext) {
      content.remove(content.size() - 1);
    }

    return new SliceImpl(content, pageable, isNext);
  }

  private Set<Predicate> getPredicates(CriteriaBuilder builder, Root<Bouquet> root, boolean showAll, Integer id, FilterRequest request) {
    Set<Predicate> predicates = new HashSet<>();
    if (showAll) {
      predicates.add(builder.equal(root.get(Bouquet_.active), true));
      predicates.add(builder.equal(root.join(Bouquet_.categories).get(Category_.active), true));
    }

    if (id != null) {
      predicates.add(builder.equal(root.join(Bouquet_.categories).get(Category_.id), id));
    }
    else {
      predicates.add(root.join(Bouquet_.categories).isNotNull());
    }

    if (request.isFlowers()) {
      predicates.add(root.join(Bouquet_.bouquetFlowerCount).get(BouquetFlowerCount_.flower).get(Flower_.id).in(request.getFlowers()));
    }
    if (request.isColors()) {
      predicates.add(root.join(Bouquet_.colors).in(request.getColors()));
    }
    if (request.isSizes()) {
      predicates.add(root.get(Bouquet_.size).in(request.getSizes()));
    }
    if (request.isPrices()) {
      predicates.add(builder.between(root.get(Bouquet_.price), request.getPriceMin(), request.getPriceMax()));
    }
    return predicates;
  }
}
