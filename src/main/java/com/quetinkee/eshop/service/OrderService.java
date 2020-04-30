package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.OrderDao;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderDao dao;

    @Autowired
    public OrderService(OrderDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Order find(Integer id) {
        Optional<Order> order = this.dao.findById(id);
        return order.isPresent() ? order.get() : null;
    }

    @Transactional(readOnly = true)
    public Order find(Integer id, boolean activeOnly) {
        if (activeOnly) return this.dao.findByIdAndActiveTrue(id);
        return this.find(id);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll(boolean active) {
        if (active) return this.dao.findAllByActiveTrue();
        return this.dao.findAll();
    }

    @Transactional(readOnly = true)
    public Slice<Order> findAll(Integer pageNum, Integer pageSize, boolean active) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("name"));
        if (active) return this.dao.findAllByActiveTrue(paging);
        return this.dao.findAllBy(paging);
    }

    @Transactional
    public void update(Order order) {
        Objects.requireNonNull(order);
        this.dao.save(order);
    }

    @Transactional
    public void delete (Order order) {
        Objects.requireNonNull(order);
        this.dao.delete(order);
    }

    @Transactional
    public void persist(Order order) {
        Objects.requireNonNull(order);
        this.dao.save(order);
    }
}
