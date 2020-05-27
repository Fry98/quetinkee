package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.InventoryDao;
import com.quetinkee.eshop.model.Inventory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Slice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class InventoryServiceTest {
    @Mock
    InventoryDao dao;
    @InjectMocks
    InventoryService inventoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testFindAll2() throws Exception {
        when(dao.findAllBy(any())).thenReturn(null);
        when(dao.findAllByActiveTrue(any())).thenReturn(null);

        Slice<Inventory> result = inventoryService.findAll(1, 1, true);
        Assert.assertNull(result);
    }

    @Test
    public void testUpdate() throws Exception {
        inventoryService.update(new Inventory("name", true));
    }

    @Test
    public void testDelete() throws Exception {
        inventoryService.delete(new Inventory("name", true));
    }

    @Test
    public void testPersist() throws Exception {
        inventoryService.persist(new Inventory("name", true));
    }
}