package com.quetinkee.eshop.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
import java.util.Collection;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static junit.framework.TestCase.*;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/test.properties")   // add custom application settings
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserDao dao;

    // allow password get for js parser
    @JsonIgnoreProperties(value = "password", allowGetters = true, allowSetters = true)
    private class UserTest extends User {
    }

    //@Sql("test.sql")
    @Test
    public void createNewUserTest() throws JsonProcessingException{
        //User newUser = new User("Zhigalo" , "Ebanoye" , "ebazhi@yoba.com" , "hersosi2002" , "123456789");
        User newUser = new UserTest();
        newUser.setFirstName("Zhigalo");
        newUser.setLastName("Ebanoye");
        newUser.setMail("ebazhi@yoba.com");
        newUser.setPassword("hersosi2002");
        newUser.setPhone("123456789");
        newUser.setAddressDelivery(new Address("Street", "City", "12345"));

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", newUser, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        assertEquals(result.getBody(), saved.get().getId());
        assertEquals("Zhigalo" , saved.get().getFirstName());
        assertEquals("Ebanoye" , saved.get().getLastName());
        assertEquals("ebazhi@yoba.com" , saved.get().getMail());
        assertNotNull(saved.get().getPassword());
        assertEquals("123456789" , saved.get().getPhone());
    }
/*
    @Test
    public void updateUserTest(){
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

        User updateFrom = new User("Pidrilo" , "Ebanoye" , "ebapidr@yoba.com" , "sosipisyu2002" , "123456788");

        User updatedUser = usrCntrl.updateId(response.getBody().getId() , updateFrom );

        assertNotNull(updatedUser);
        assertEquals("Pidrilo" , updatedUser.getFirstName());
        assertEquals("Ebanoye" , updatedUser.getLastName());
        assertEquals("ebapidr@yoba.com" , updatedUser.getMail());
        assertEquals("sosipisyu2002" , updatedUser.getPassword());
        assertEquals("123456788" , updatedUser.getPhone());

    }

    @Test
    public void deleteUserTest(){
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

        usrCntrl.deleteId(response.getBody().getId());

        assertNull(usrCntrl.getId(response.getBody().getId()));
    }

    @Test
    public void createBillingAdressTest(){
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

        Address addr = new Address();
        addr.setCity("Praha");
        addr.setStreet("Na Lysine");
        addr.setZip("15200");

        usrCntrl.createAddressBillingId(response.getBody().getId() , addr);

        assertNotNull(response.getBody().getAddressBilling());
    }

    @Test
    public void createDeliverAdressTest(){
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

        Address addr = new Address();
        addr.setCity("Praha");
        addr.setStreet("Na Lysine");
        addr.setZip("15200");

        usrCntrl.createAddressDeliveryId(response.getBody().getId() , addr);

        assertNotNull(response.getBody().getAddressDelivery());
    }

    @Test
    public void updateBillingAddressTest(){
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

        Address addr = new Address();
        addr.setCity("Praha");
        addr.setStreet("Na Lysine");
        addr.setZip("15200");

        usrCntrl.createAddressBillingId(response.getBody().getId() , addr);

        Address addre = new Address();
        addr.setCity("Brno");
        addr.setStreet("Super popa");
        addr.setZip("1447");

        usrCntrl.updateAddressBillingId(response.getBody().getId() , addre);

        assertEquals(response.getBody().getAddressBilling().getCity(), addre.getCity());
        assertEquals(response.getBody().getAddressBilling().getStreet(), addre.getStreet());
        assertEquals(response.getBody().getAddressBilling().getZip() , addre.getZip());
    }

    @Test
    public void updateDeliveryAddressTest(){
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

        Address addr = new Address();
        addr.setCity("Praha");
        addr.setStreet("Na Lysine");
        addr.setZip("15200");

        usrCntrl.createAddressDeliveryId(response.getBody().getId() , addr);

        Address addre = new Address();
        addr.setCity("Brno");
        addr.setStreet("Super popa");
        addr.setZip("1447");

        usrCntrl.updateAddressDeliveryId(response.getBody().getId() , addre);

        assertEquals(response.getBody().getAddressDelivery().getCity(), addre.getCity());
        assertEquals(response.getBody().getAddressDelivery().getStreet(), addre.getStreet());
        assertEquals(response.getBody().getAddressDelivery().getZip() , addre.getZip());
    }
*/
}
