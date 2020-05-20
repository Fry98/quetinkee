package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.FlowersInStock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface InventoryDao extends JpaRepository<FlowersInStock, Integer> {

    Slice<FlowersInStock> findAllBy(Pageable pageable);

    Set<FlowersInStock> findAllByFlowerIdIn(Set<Integer> keySet);
}