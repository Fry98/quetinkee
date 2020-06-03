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

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", newUser, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        User updateFrom = new User("Pidrilo" , "Ebanoye" , "ebapidr@yoba.com" , "sosipisyu2002" , "123456788");

        testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").delete("/api/users", newUser);

        ResponseEntity<Integer> update = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", updateFrom, Integer.class);
        assertNotNull(update.getBody());
        //assertEquals(HttpStatus.CREATED, update.getStatusCode());

        assertEquals(HttpStatus.OK, update.getStatusCode());

        Optional<User> saved = dao.findById(update.getBody());
        assertTrue(saved.isPresent());

        assertEquals(updateFrom.getFirstName() , saved.get().getFirstName());
        assertEquals(updateFrom.getLastName() , saved.get().getLastName());
        assertEquals(updateFrom.getMail() , saved.get().getMail());
        assertEquals(updateFrom.getPassword() , saved.get().getPassword());
        assertEquals(updateFrom.getPhone() , saved.get().getPhone());

    }

    @Test
    public void deleteUserTest(){
        //User newUser = new User("Zhigalo" , "Ebanoye" , "ebazhi@yoba.com" , "hersosi2002" , "123456789");
        User toDelete = new User();
        toDelete.setFirstName("Zhigalo");
        toDelete.setLastName("Ebanoye");
        toDelete.setMail("ebazhi@yoba.com");
        toDelete.setPassword("hersosi2002");
        toDelete.setPhone("123456789");
        //HttpEntity<User> request = new HttpEntity<>(newUser);
        //ResponseEntity<User> response = testRestTemplate.postForEntity("/user" , request , User.class);

        ResponseEntity<Integer> resultNew = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", toDelete, Integer.class);
        assertNotNull(resultNew.getBody());
        assertEquals(HttpStatus.CREATED, resultNew.getStatusCode());

        Optional<User> saved = dao.findById(resultNew.getBody());
        assertTrue(saved.isPresent());

        testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").delete("/api/users", toDelete, Integer.class);
        Optional<User> find = dao.findById(resultNew.getBody());
        assertFalse(find.isPresent());
    }

    @Test
    public void createBillingAdressTest(){
        //User newUser = new User("Zhigalo" , "Ebanoye" , "ebazhi@yoba.com" , "hersosi2002" , "123456789");
        UserController controller = new UserController();
        User newUser = new User();
        newUser.setFirstName("Zhigalo");
        newUser.setLastName("Ebanoye");
        newUser.setMail("ebazhi@yoba.com");
        newUser.setPassword("hersosi2002");
        newUser.setPhone("123456789");
        //HttpEntity<User> request = new HttpEntity<>(newUser);
        //ResponseEntity<User> response = testRestTemplate.postForEntity("/user" , request , User.class);

        Address address = new Address();
        address.setCity("Praha");
        address.setStreet("Na Lysine");
        address.setZip("15200");

        controller.createAddressBillingId(newUser.getId(), address);

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", newUser, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        assertEquals(address.getCity(), saved.get().getAddressBilling().getCity());
        assertEquals(address.getStreet(), saved.get().getAddressBilling().getStreet());
        assertEquals(address.getZip(), saved.get().getAddressBilling().getZip());
    }

    @Test
    public void createDeliverAdressTest(){
        //User newUser = new User("Zhigalo" , "Ebanoye" , "ebazhi@yoba.com" , "hersosi2002" , "123456789");
        UserController controller = new UserController();
        User newUser = new User();
        newUser.setFirstName("Zhigalo");
        newUser.setLastName("Ebanoye");
        newUser.setMail("ebazhi@yoba.com");
        newUser.setPassword("hersosi2002");
        newUser.setPhone("123456789");
        //HttpEntity<User> request = new HttpEntity<>(newUser);
        //ResponseEntity<User> response = testRestTemplate.postForEntity("/user" , request , User.class);

        Address address = new Address();
        address.setCity("Praha");
        address.setStreet("Na Pysine");
        address.setZip("15300");

        controller.createAddressDeliveryId(newUser.getId(), address);

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", newUser, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        assertEquals(address.getCity(), saved.get().getAddressDelivery().getCity());
        assertEquals(address.getStreet(), saved.get().getAddressDelivery().getStreet());
        assertEquals(address.getZip(), saved.get().getAddressDelivery().getZip());
    }

    @Test
    public void updateBillingAddressTest(){
        //User newUser = new User("Zhigalo" , "Ebanoye" , "ebazhi@yoba.com" , "hersosi2002" , "123456789");
        UserController controller = new UserController();
        User newUser = new User();
        newUser.setFirstName("Zhigalo");
        newUser.setLastName("Ebanoye");
        newUser.setMail("ebazhi@yoba.com");
        newUser.setPassword("hersosi2002");
        newUser.setPhone("123456789");
        //HttpEntity<User> request = new HttpEntity<>(newUser);
        //ResponseEntity<User> response = testRestTemplate.postForEntity("/user" , request , User.class);

        Address address = new Address();
        address.setCity("Praha");
        address.setStreet("Na Lysine");
        address.setZip("15200");

        controller.createAddressBillingId(newUser.getId(), address);

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", newUser, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        assertEquals(address.getCity(), saved.get().getAddressBilling().getCity());
        assertEquals(address.getStreet(), saved.get().getAddressBilling().getStreet());
        assertEquals(address.getZip(), saved.get().getAddressBilling().getZip());

        address.setCity("Ostrava");
        address.setStreet("Na Oysine");
        address.setZip("15400");

        controller.updateAddressBillingId(newUser.getId(), address);

        ResponseEntity<Integer> update = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", newUser, Integer.class);
        assertNotNull(update.getBody());
        assertEquals(HttpStatus.CREATED, update.getStatusCode());

        assertEquals(address.getCity(), saved.get().getAddressBilling().getCity());
        assertEquals(address.getStreet(), saved.get().getAddressBilling().getStreet());
        assertEquals(address.getZip(), saved.get().getAddressBilling().getZip());
    }

    @Test
    public void updateDeliveryAddressTest() {
        //User newUser = new User("Zhigalo" , "Ebanoye" , "ebazhi@yoba.com" , "hersosi2002" , "123456789");
        UserController controller = new UserController();
        User newUser = new User();
        newUser.setFirstName("Zhigalo");
        newUser.setLastName("Ebanoye");
        newUser.setMail("ebazhi@yoba.com");
        newUser.setPassword("hersosi2002");
        newUser.setPhone("123456789");
        //HttpEntity<User> request = new HttpEntity<>(newUser);
        //ResponseEntity<User> response = testRestTemplate.postForEntity("/user" , request , User.class);

        Address address = new Address();
        address.setCity("Praha");
        address.setStreet("Na Lysine");
        address.setZip("15200");

        controller.createAddressDeliveryId(newUser.getId(), address);

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", newUser, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        assertEquals(address.getCity(), saved.get().getAddressDelivery().getCity());
        assertEquals(address.getStreet(), saved.get().getAddressDelivery().getStreet());
        assertEquals(address.getZip(), saved.get().getAddressDelivery().getZip());

        address.setCity("Ostrava");
        address.setStreet("Na Oysine");
        address.setZip("15400");

        controller.createAddressDeliveryId(newUser.getId(), address);

        ResponseEntity<Integer> update = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", newUser, Integer.class);
        assertNotNull(update.getBody());
        assertEquals(HttpStatus.CREATED, update.getStatusCode());

        assertEquals(address.getCity(), saved.get().getAddressDelivery().getCity());
        assertEquals(address.getStreet(), saved.get().getAddressDelivery().getStreet());
        assertEquals(address.getZip(), saved.get().getAddressDelivery().getZip());
    }
}
