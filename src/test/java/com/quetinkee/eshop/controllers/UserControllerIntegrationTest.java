package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static junit.framework.TestCase.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    //@Sql("test.sql")
    @Test
    public void createNewUserTest(){
        UserController usrCntrl = new UserController();
        //User newUser = new User("Zhigalo" , "Ebanoye" , "ebazhi@yoba.com" , "hersosi2002" , "123456789");
        User newUser = new User();
        newUser.setFirstName("Zhigalo");
        newUser.setLastName("Ebanoye");
        newUser.setMail("ebazhi@yoba.com");
        newUser.setPassword("hersosi2002");
        newUser.setPhone("123456789");
        //HttpEntity<User> request = new HttpEntity<>(newUser);
        //ResponseEntity<User> response = testRestTemplate.postForEntity("/user" , request , User.class);

        ResponseEntity<User> response = usrCntrl.create(newUser);


        assertNotNull(response.getBody().getId());
        assertEquals("Zhigalo" , response.getBody().getFirstName());
        assertEquals("Ebanoye" , response.getBody().getLastName());
        assertEquals("ebazhi@yoba.com" , response.getBody().getMail());
        assertEquals("hersosi2002" , response.getBody().getPassword());
        assertEquals("123456789" , response.getBody().getPhone());
    }
}
