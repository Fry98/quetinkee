package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Order;
import com.quetinkee.eshop.model.projection.OrderList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends GenericDao<Order, OrderList> {

    public Slice<OrderList> findAllByUserId(Integer id, Pageable paging);

}
