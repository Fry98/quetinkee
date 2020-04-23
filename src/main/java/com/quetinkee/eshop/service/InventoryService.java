package com.quetinkee.eshop.service;


import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.dao.InventoryDao;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Inventory;
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
    public Inventory find(Integer id) {
        Optional<Inventory> inventory = this.dao.findById(id);
        return inventory.isPresent() ? inventory.get() : null;
    }

    @Transactional(readOnly = true)
    public Inventory find(Integer id, boolean activeOnly) {
        if (activeOnly) return this.dao.findByIdAndActiveTrue(id);
        return this.find(id);
    }

    @Transactional(readOnly = true)
    public List<Inventory> findAll(boolean active) {
        if (active) return this.dao.findAllByActiveTrue();
        return this.dao.findAll();
    }

    @Transactional(readOnly = true)
    public Slice<Inventory> findAll(Integer pageNum, Integer pageSize, boolean active) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("name"));
        if (active) return this.dao.findAllByActiveTrue(paging);
        return this.dao.findAllBy(paging);
    }

    @Transactional
    public void update(Inventory inventory) {
        Objects.requireNonNull(inventory);
        this.dao.save(inventory);
    }

    @Transactional
    public void delete (Inventory inventory) {
        Objects.requireNonNull(inventory);
        this.dao.delete(inventory);
    }

    @Transactional
    public void persist(Inventory inventory) {
        Objects.requireNonNull(inventory);
        this.dao.save(inventory);
    }
}

