package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.OrderDao;
import com.quetinkee.eshop.model.Order;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.model.projection.OrderList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Mock
    OrderDao dao;
    @Mock
    Sort sort;
    @Mock
    Validator validator;
    @InjectMocks
    OrderService orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSlice() throws Exception {
        Slice<OrderList> result = orderService.getSlice(1, 1);
        Assert.assertNull(result);
    }
}