package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.model.Review;
import com.quetinkee.eshop.model.projection.ReviewList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewDao extends JpaRepository<Review, Integer>{

  @Query(value = "SELECT r.rating AS rating, r.message AS message, r.created AS created, u.lastName AS userName FROM Review r JOIN r.user AS u WHERE r.bouquet = ?1")
  Slice<ReviewList> findAllByBouquet(Bouquet bouquet, Pageable pageable);

  @Query(value = "SELECT AVG(r.rating) FROM Review r WHERE r.bouquet = ?1 GROUP BY r.bouquet")
  Float findAvgRating(Bouquet bouquet);

  Review findByBouquetIdAndUserId(Integer bouquetId, Integer userId);
}
