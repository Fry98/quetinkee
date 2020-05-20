package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.dao.BouquetDao;
import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.dao.InventoryDao;
import com.quetinkee.eshop.model.BouquetFlowerCount;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.FlowersInStock;
import com.quetinkee.eshop.model.OrderItem;
import com.quetinkee.eshop.model.projection.BouquetFlowerCountList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Storage {

    private final InventoryDao inventoryDao;
    private final BouquetDao bouquetDao;
    private final FlowerDao flowerDao;

    @Autowired
    public Storage(InventoryDao inventoryDao, BouquetDao bouquetDao, FlowerDao flowerDao) {
        this.inventoryDao = inventoryDao;
        this.bouquetDao = bouquetDao;
        this.flowerDao = flowerDao;
    }

    @Transactional
    public void reserveFlowers(Set<OrderItem> items) {
        Objects.requireNonNull(items);
        Map<Integer,Integer> flowers = this.getFlowerCounts(items);

        // reserve existing flowers
        Set<FlowersInStock> storage = this.inventoryDao.findAllByFlowerIdIn(flowers.keySet());
        for (FlowersInStock stock : storage) {
            stock.addReserved(flowers.get(stock.getFlower().getId()));
            flowers.remove(stock.getFlower().getId());
            this.inventoryDao.save(stock);
        }

        // add new
        for (Map.Entry<Integer, Integer> rec : flowers.entrySet()) {
            Optional<Flower> flower = this.flowerDao.findById(rec.getKey());
            if (flower.isPresent()) {
                FlowersInStock stock = new FlowersInStock(flower.get(), 0);
                stock.addReserved(rec.getValue());
                this.inventoryDao.save(stock);
            }
            else {
                throw new ValidationException("Neexistující položka pro skladování");
            }
        }
    }

    @Transactional
    public void freeFlowers(Set<OrderItem> items) {
        Objects.requireNonNull(items);
        Map<Integer,Integer> flowers = this.getFlowerCounts(items);

        Set<FlowersInStock> storage = this.inventoryDao.findAllByFlowerIdIn(flowers.keySet());
        for (FlowersInStock stock : storage) {
            stock.freeReserved(flowers.get(stock.getFlower().getId()));
            this.inventoryDao.save(stock);
        }
    }

    @Transactional
    public void consumeFlowers(Set<OrderItem> items) {
        Objects.requireNonNull(items);
        Map<Integer,Integer> flowers = this.getFlowerCounts(items);

        Set<FlowersInStock> storage = this.inventoryDao.findAllByFlowerIdIn(flowers.keySet());
        for (FlowersInStock stock : storage) {
            stock.consume(flowers.get(stock.getFlower().getId()));
            this.inventoryDao.save(stock);
        }
    }

    public Map<Integer,Integer> itemsInStock(Set<OrderItem> items) {
        Objects.requireNonNull(items);
        Map<Integer,Integer> bouquets = new HashMap<>();
        for (OrderItem item : items) {
            bouquets.put(item.getBouquet().getId(), item.getQuantity());
        }
        return this.itemsInStock(bouquets);
    }

    /**
     * Return null if its everything in storage
     * @param bouquets
     * @return
     */
    @Transactional(readOnly = true)
    public Map<Integer,Integer> itemsInStock(Map<Integer,Integer> bouquets) {
        Objects.requireNonNull(bouquets);

        Set<BouquetFlowerCountList> counters = this.bouquetDao.findCountsAllByIdIn(bouquets.keySet());
        Map<Integer,Integer> flowers = this.getFlowerTotalCounts(counters, bouquets);
        Set<FlowersInStock> storage = this.inventoryDao.findAllByFlowerIdIn(flowers.keySet());

        // check if everything is in storage
        if (this.haveEverythingInStock(storage, flowers)) return null;

        // else try count possible bouquets
        Map<Integer,Integer> output = new HashMap<>();
        while (!bouquets.isEmpty()) {
            boolean found = false;

          Iterator<Map.Entry<Integer, Integer>> it = bouquets.entrySet().iterator();
          while (it.hasNext()) {
              Map.Entry<Integer, Integer> bouquet = it.next();


//          for (Map.Entry<Integer, Integer> bouquet : bouquets.entrySet()) {
              Set<BouquetFlowerCountList> flowersInBouquet = counters.stream().filter(item -> item.getBouquetId() == bouquet.getKey()).collect(Collectors.toSet());
              if (this.tryConsume(flowersInBouquet, storage)) {
                  if (output.containsKey(bouquet.getKey())) {
                      output.put(bouquet.getKey(), output.get(bouquet.getKey()) + 1);
                  }
                  else {
                      output.put(bouquet.getKey(), 1);
                  }
                  bouquets.replace(bouquet.getKey(), bouquet.getValue() - 1);
                  if (bouquets.get(bouquet.getKey()) == 0) it.remove();
                  found = true;
              }
          }

          if (found == false) break;
        }
        return output;
    }



    /**
     * Information for detail
     * @param flowers
     * @return possible bouquet count in storage
     */
    @Transactional(readOnly = true)
    public Integer countBouquetsInStock(Set<BouquetFlowerCount> flowers) {
        Objects.requireNonNull(flowers);

        Integer min = 10;
        Map<Integer,Integer> map = new HashMap<>();
        flowers.forEach(item -> { map.put(item.getFlower().getId(), item.getCount()); });

        Set<FlowersInStock> storage = this.inventoryDao.findAllByFlowerIdIn(map.keySet());
        if (storage.size() != map.size()) return 0;

        Integer current;
        for (FlowersInStock stock : storage) {
          System.out.println(stock.getCount() + " - " + stock.getReserved() + " / " + map.get(stock.getFlower().getId()));
          current = (stock.getCount() - stock.getReserved()) / map.get(stock.getFlower().getId());
          if (min > current) {
            min = current;
            if (min <= 0) return 0;
          }
        }
        return min;
    }

    private Map<Integer,Integer> getFlowerCounts(Set<OrderItem> items) {
        Map<Integer,Integer> map = new HashMap<>();
        for (OrderItem item : items) {
            map.put(item.getBouquet().getId(), item.getQuantity());
        }
        // bouquet can be without flowers
        if (!map.isEmpty()) {
            Set<BouquetFlowerCountList> counters = this.bouquetDao.findCountsAllByIdIn(map.keySet());
            return this.getFlowerTotalCounts(counters, map);
        }
        return map;
    }

    private Map<Integer,Integer> getFlowerTotalCounts(Set<BouquetFlowerCountList> counters, Map<Integer,Integer> map) {
        Map<Integer,Integer> flowers = new HashMap<>();
        for (BouquetFlowerCountList item : counters) {
          System.out.println(item);
          System.out.println(item.getBouquetId() + " x " + item.getCount() + " x " + item.getBouquetId());
          System.out.println("map " + map.get(item.getBouquetId()));
            if (flowers.containsKey(item.getFlowerId())) {
                flowers.put(item.getFlowerId(), flowers.get(item.getFlowerId()) + (item.getCount() * map.get(item.getBouquetId())));
            }
            else {
                flowers.put(item.getFlowerId(), item.getCount() * map.get(item.getBouquetId()));
            }
        }
        return flowers;
    }

    private boolean haveEverythingInStock(Set<FlowersInStock> storage, Map<Integer,Integer> flowers) {
        for (Map.Entry<Integer, Integer> flower : flowers.entrySet()) {
          Optional<FlowersInStock> stock = storage.stream().filter(item -> item.getFlower().getId() == flower.getKey()).findFirst();
          if (!stock.isPresent() || (stock.get().getCount() - stock.get().getReserved()) < flower.getValue()) return false;
        }
        return true;
    }

    private boolean tryConsume(Set<BouquetFlowerCountList> flowersInBouquet, Set<FlowersInStock> storage) {
        // check if enought flowers
        for (BouquetFlowerCountList flower : flowersInBouquet) {
            Optional<FlowersInStock> stock = storage.stream().filter(item -> item.getFlower().getId() == flower.getFlowerId()).findFirst();
            if (!stock.isPresent() || (stock.get().getCount() - stock.get().getReserved()) < flower.getCount()) return false;
        }
        // then consume them
        for (BouquetFlowerCountList flower : flowersInBouquet) {
            Optional<FlowersInStock> stock = storage.stream().filter(item -> item.getFlower().getId() == flower.getFlowerId()).findFirst();
            stock.get().addReserved(flower.getCount());
        }
        return true;
    }
}
