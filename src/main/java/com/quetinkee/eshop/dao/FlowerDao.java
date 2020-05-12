package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.projection.FlowerList;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerDao extends GenericDao<Flower, FlowerList> {

}
