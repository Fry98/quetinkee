package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.FlowersInStock;
import com.quetinkee.eshop.model.projection.FlowersInStockList;
import com.quetinkee.eshop.model.projection.FlowersToRestockList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface InventoryDao extends JpaRepository<FlowersInStock, Integer> {

    @Query(value = "SELECT fis.id AS id, fis.minCount AS minCount, fis.count AS count, fis.reserved AS reserved, (fis.count - fis.reserved) AS free, f.name AS name FROM FlowersInStock fis JOIN fis.flower AS f")
    Slice<FlowersInStockList> findAllBy(Pageable pageable);

    @Query(value = "SELECT fis.id AS id, f.name AS name, ABS(fis.count - fis.minCount - fis.reserved) AS restock FROM FlowersInStock fis JOIN fis.flower AS f WHERE (fis.count - fis.minCount - fis.reserved) < 0")
    Set<FlowersToRestockList> findRestockAll(Sort sort);

    Set<FlowersInStock> findAllByFlowerIdIn(Set<Integer> keySet);
}