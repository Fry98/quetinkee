package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.InventoryDao;
import com.quetinkee.eshop.model.Flower_;
import com.quetinkee.eshop.model.FlowersInStock;
import com.quetinkee.eshop.model.projection.FlowersInStockList;
import com.quetinkee.eshop.model.projection.FlowersToRestockList;
import com.quetinkee.eshop.utils.helpers.FlowerSInStockEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
    public Set<FlowersToRestockList> getWhatRestock() {
        return this.dao.findRestockAll(Sort.by(Flower_.NAME));
    }

    @Transactional(readOnly = true)
    public Slice<FlowersInStockList> findAll(Integer pageNum, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Flower_.NAME));
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

  public Set<FlowersInStockList> updateStock(Set<FlowerSInStockEdit> newStock) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}

