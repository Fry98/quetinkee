package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.InventoryDao;
import com.quetinkee.eshop.model.FlowersInStock;
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
public class InventoryService {

    private final InventoryDao dao;

    @Autowired
    public InventoryService(InventoryDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public FlowersInStock find(Integer id) {
        Optional<FlowersInStock> inventory = this.dao.findById(id);
        return inventory.isPresent() ? inventory.get() : null;
    }

    @Transactional(readOnly = true)
    public Slice<FlowersInStock> findAll(Integer pageNum, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("name"));
        return this.dao.findAllBy(paging);
    }

    @Transactional
    public void update(FlowersInStock inventory) {
        Objects.requireNonNull(inventory);
        this.dao.save(inventory);
    }

    @Transactional
    public void delete (FlowersInStock inventory) {
        Objects.requireNonNull(inventory);
        this.dao.delete(inventory);
    }

    @Transactional
    public void persist(FlowersInStock inventory) {
        Objects.requireNonNull(inventory);
        this.dao.save(inventory);
    }
}

