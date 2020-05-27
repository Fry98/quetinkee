package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.AddressDao;
import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.model.projection.UserList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    AddressDao addressDao;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserDao dao;
    @Mock
    Sort sort;
    @Mock
    Validator validator;
    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckPassword() throws Exception {
        boolean result = userService.checkPassword("password");
        Assert.assertTrue(result);
    }

    @Test
    public void testEncodePassword() throws Exception {
        userService.encodePassword(new User(null, null, null, "password", null));
    }

    @Test
    public void testDelete() throws Exception {
        userService.delete(new User("firstName", "lastName", "mail", "password", "phone"));
    }

    @Test
    public void testUpdateAddress() throws Exception {
        userService.updateAddress(new Address("street", "city", "zip"), new Address("street", "city", "zip"));
    }

    @Test
    public void testDelete2() throws Exception {
        userService.delete(new Address("street", "city", "zip"));
    }

    @Test
    public void testPersistAddress() throws Exception {
        userService.persistAddress(new Address("street", "city", "zip"));
    }

    @Test
    public void testGetSlice() throws Exception {
        Slice<UserList> result = userService.getSlice(1, 1);
        Assert.assertNull(result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme