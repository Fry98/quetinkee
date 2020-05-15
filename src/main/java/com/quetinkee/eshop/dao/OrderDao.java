package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.Order;
import com.quetinkee.eshop.model.OrderAddress;
import com.quetinkee.eshop.model.projection.OrderList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends GenericDao<Order, OrderList> {

}
