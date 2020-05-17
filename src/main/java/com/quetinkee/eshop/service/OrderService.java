package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.dao.OrderDao;
import com.quetinkee.eshop.model.*;
import com.quetinkee.eshop.model.projection.FlowerList;
import com.quetinkee.eshop.model.projection.OrderList;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService extends GenericAdminService<OrderDao, Order, OrderList>{

    @Autowired
    public OrderService(Validator validator, OrderDao dao) {
        super(validator, dao, Sort.by(Order_.ID));
    }

    @Transactional
    @Override
    public Order update(Order original, Order newData) {
        Objects.requireNonNull(original);
        Objects.requireNonNull(newData);

        if (newData.getUser() != null) original.setUser(newData.getUser());

        if (this.validate(original)) return this.dao.save(original);
        return null;
    }
}
