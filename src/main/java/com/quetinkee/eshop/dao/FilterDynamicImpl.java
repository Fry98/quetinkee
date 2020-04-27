package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Boquet;
import com.quetinkee.eshop.model.BoquetFlowerCount_;
import com.quetinkee.eshop.model.Boquet_;
import com.quetinkee.eshop.model.Category_;
import com.quetinkee.eshop.model.Flower_;
import com.quetinkee.eshop.model.projection.BoquetList;
import com.quetinkee.eshop.model.projection.BoquetListImpl;
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
  public Slice<BoquetList> findResults(boolean showAll, Integer id, FilterRequest request, Pageable pageable) {
    CriteriaBuilder builder = this.em.getCriteriaBuilder();
    CriteriaQuery<BoquetListImpl> query = builder.createQuery(BoquetListImpl.class);
    Root<Boquet> root = query.from(Boquet.class);
    CompoundSelection<BoquetListImpl> projection = builder.construct(BoquetListImpl.class, root.get(Boquet_.id), root.get(Boquet_.name), root.get(Boquet_.price), root.get(Boquet_.size), root.get(Boquet_.active));

    // set predicates
    Set<Predicate> predicates = this.getPredicates(builder, root, showAll, id, request);

    // query
    List<BoquetListImpl> content = this.em.createQuery(query.where(predicates.toArray(new Predicate[predicates.size()])).distinct(true).orderBy(builder.asc(root.get(Boquet_.name))).select(projection))
            .setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize() + 1).getResultList();

    // find if there is more items
    boolean isNext = content.size() > pageable.getPageSize();
    if (isNext) {
      content.remove(content.size() - 1);
    }

    return new SliceImpl(content, pageable, isNext);
  }

  private Set<Predicate> getPredicates(CriteriaBuilder builder, Root<Boquet> root, boolean showAll, Integer id, FilterRequest request) {
    Set<Predicate> predicates = new HashSet<>();
    if (showAll) {
      predicates.add(builder.equal(root.get(Boquet_.active), true));
      predicates.add(builder.equal(root.join(Boquet_.categories).get(Category_.active), true));
    }

    if (id != null) {
      predicates.add(builder.equal(root.join(Boquet_.categories).get(Category_.id), id));
    }
    else {
      predicates.add(root.join(Boquet_.categories).isNotNull());
    }

    if (request.isFlowers()) {
      predicates.add(root.join(Boquet_.boquetflowerCount).get(BoquetFlowerCount_.flower).get(Flower_.id).in(request.getFlowers()));
    }
    if (request.isColors()) {
      predicates.add(root.join(Boquet_.colors).in(request.getColors()));
    }
    if (request.isSizes()) {
      predicates.add(root.get(Boquet_.size).in(request.getSizes()));
    }
    if (request.isPrices()) {
      predicates.add(builder.between(root.get(Boquet_.price), request.getPriceMin(), request.getPriceMax()));
    }
    return predicates;
  }
}
