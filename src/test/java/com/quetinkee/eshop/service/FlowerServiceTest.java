package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.projection.FlowerList;
import com.quetinkee.eshop.model.projection.OptionList;
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

public class FlowerServiceTest {
    @Mock
    FlowerDao dao;
    @Mock
    Sort sort;
    @Mock
    Validator validator;
    @InjectMocks
    FlowerService flowerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetSlice() throws Exception {
        Slice<FlowerList> result = flowerService.getSlice(1, 1);
        Assert.assertEquals(null, result);
    }
}