package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.CategoryDao;
import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.projection.CategoryList;
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

public class CategoryServiceTest {
    @Mock
    CategoryDao dao;
    @Mock
    Sort sort;
    @Mock
    Validator validator;
    @InjectMocks
    CategoryService categoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetSlice() throws Exception {
        Slice<CategoryList> result = categoryService.getSlice(1, 1);
        Assert.assertNull(result);
    }


    @Test
    public void testDelete() throws Exception {
        categoryService.delete(new Category("name", Integer.valueOf(0), Boolean.TRUE));
    }

}

