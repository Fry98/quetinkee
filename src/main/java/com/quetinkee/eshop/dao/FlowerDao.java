package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerDao extends JpaRepository<Flower, Integer> {

}
