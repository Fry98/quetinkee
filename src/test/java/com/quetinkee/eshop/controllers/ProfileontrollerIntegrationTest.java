package com.quetinkee.eshop.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static junit.framework.TestCase.*;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/test.properties")   // add custom application settings
public class ProfileontrollerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserDao dao;

    @Test
    public void registerNewUserTest(){
        User newUser = new UserTest();
        newUser.setFirstName("Zhigalo");
        newUser.setLastName("Ebanoye");
        newUser.setMail("ebazhi@yobaa.com");
        newUser.setPassword("hersosi2002");
        newUser.setPhone("123456789");
        newUser.setAddressDelivery(new Address("Street", "City", "12345"));

        ResponseEntity<Integer> response = testRestTemplate.postForEntity("/api/profile", newUser, Integer.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Optional<User> saved = dao.findById(response.getBody());
        assertTrue(saved.isPresent());

        assertNotNull(saved.get().getId());
        assertEquals("Zhigalo" , saved.get().getFirstName());
        assertEquals("Ebanoye" , saved.get().getLastName());
        assertEquals("ebazhi@yobaa.com" , saved.get().getMail());
        assertEquals("123456789" , saved.get().getPhone());
    }

    // allow password get for js parser
    @JsonIgnoreProperties(value = "password", allowGetters = true, allowSetters = true)
    private class UserTest extends User {
    }
}
