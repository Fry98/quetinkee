package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Inventory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryDao extends JpaRepository<Inventory, Integer> {

    Inventory findByIdAndActiveTrue(Integer id);

    Slice<Inventory> findAllBy(Pageable pageable);

    Slice<Inventory> findAllByActiveTrue(Pageable pageable);

    List<Inventory> findAllByActiveTrue();
}