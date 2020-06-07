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

    @Test
    public void createNewUserTest() throws JsonProcessingException{
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
        User toUpdate = new User();
        toUpdate.setFirstName("Ivan");
        toUpdate.setLastName("Ivanov");
        toUpdate.setMail("ivaiva@yoba.com");
        toUpdate.setPassword("ivanivanov");
        toUpdate.setPhone("694374166");

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", toUpdate, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        User updateFrom = new User("Pidrilo" , "Ebanoye" , "ebapidr@yoba.com" , "sosipisyu2002" , "123456788");

        testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").delete("/api/users", toUpdate);

        toUpdate = updateFrom;

        ResponseEntity<Integer> update = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", toUpdate, Integer.class);
        assertNotNull(update.getBody());

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
        User toDelete = new User();
        toDelete.setFirstName("Vadim");
        toDelete.setLastName("Vadimov");
        toDelete.setMail("vadivadi@yoba.com");
        toDelete.setPassword("strongpassword");
        toDelete.setPhone("438490277");

        ResponseEntity<Integer> resultNew = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity("/api/users", toDelete, Integer.class);
        assertNotNull(resultNew.getBody());
        assertEquals(HttpStatus.CREATED, resultNew.getStatusCode());

        Optional<User> saved = dao.findById(resultNew.getBody());
        assertTrue(saved.isPresent());

        testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").delete("/api/users", toDelete);
        Optional<User> find = dao.findById(resultNew.getBody());
        assertFalse(find.isPresent());
    }

    @Test
    public void createBillingAdressTest(){
        User userCreateBilling = new User();
        userCreateBilling.setFirstName("Oleg");
        userCreateBilling.setLastName("Zakazchik");
        userCreateBilling.setMail("projektnegotov@yoba.com");
        userCreateBilling.setPassword("avansanet");
        userCreateBilling.setPhone("864663772");

        Address addressBilCr = new Address();
        addressBilCr.setCity("Kyiv");
        addressBilCr.setStreet("Peremoha");
        addressBilCr.setZip("15200");

        String url = "/api/users/" + userCreateBilling.getId() + "/billing";

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity(url, addressBilCr, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        assertEquals(addressBilCr.getCity(), saved.get().getAddressBilling().getCity());
        assertEquals(addressBilCr.getStreet(), saved.get().getAddressBilling().getStreet());
        assertEquals(addressBilCr.getZip(), saved.get().getAddressBilling().getZip());
    }

    @Test
    public void createDeliverAdressTest(){
        User userCreateDelivery = new User();
        userCreateDelivery.setFirstName("Pisi");
        userCreateDelivery.setLastName("Smol");
        userCreateDelivery.setMail("imverysmol@yoba.com");
        userCreateDelivery.setPassword("noeatmepls");
        userCreateDelivery.setPhone("758439277");

        Address addressDelCr = new Address();
        addressDelCr.setCity("Kharkiv");
        addressDelCr.setStreet("Amosova");
        addressDelCr.setZip("16171");

        String url = "/api/users/" + userCreateDelivery.getId() + "/delivery";

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity(url, addressDelCr, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        assertEquals(addressDelCr.getCity(), saved.get().getAddressDelivery().getCity());
        assertEquals(addressDelCr.getStreet(), saved.get().getAddressDelivery().getStreet());
        assertEquals(addressDelCr.getZip(), saved.get().getAddressDelivery().getZip());
    }

    @Test
    public void updateBillingAddressTest(){
        User userUpdateBil = new User();
        userUpdateBil.setFirstName("Pisi");
        userUpdateBil.setLastName("Big");
        userUpdateBil.setMail("bigpisi@yoba.com");
        userUpdateBil.setPassword("imverybig");
        userUpdateBil.setPhone("564777111");

        Address addressUpdateBil = new Address();
        addressUpdateBil.setCity("Tuapse");
        addressUpdateBil.setStreet("Pobeda");
        addressUpdateBil.setZip("15200");

        String url = "/api/users/" + userUpdateBil.getId() + "/billing";

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity(url, addressUpdateBil, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        testRestTemplate.delete(url, addressUpdateBil);

        addressUpdateBil.setCity("NewTuapse");
        addressUpdateBil.setStreet("NewPobeda");
        addressUpdateBil.setZip("87500");

        ResponseEntity<Integer> update = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity(url, addressUpdateBil, Integer.class);
        assertNotNull(update.getBody());
        assertEquals(HttpStatus.CREATED, update.getStatusCode());

        Optional<User> updatedResult = dao.findById(update.getBody());
        assertTrue(updatedResult.isPresent());

        assertEquals(addressUpdateBil.getCity(), updatedResult.get().getAddressBilling().getCity());
        assertEquals(addressUpdateBil.getStreet(), updatedResult.get().getAddressBilling().getStreet());
        assertEquals(addressUpdateBil.getZip(), updatedResult.get().getAddressBilling().getZip());
    }

    @Test
    public void updateDeliveryAddressTest() {
        User userUpdateDel = new User();
        userUpdateDel.setFirstName("Alexey");
        userUpdateDel.setLastName("Valeriev");
        userUpdateDel.setMail("alexval@yoba.com");
        userUpdateDel.setPassword("imalexey1922");
        userUpdateDel.setPhone("564733882");

        Address addressUpdateDel = new Address();
        addressUpdateDel.setCity("Moskva");
        addressUpdateDel.setStreet("Olegova");
        addressUpdateDel.setZip("84377");

        String url = "/api/users/" + userUpdateDel.getId() + "/delivery";

        ResponseEntity<Integer> result = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity(url, addressUpdateDel, Integer.class);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Optional<User> saved = dao.findById(result.getBody());
        assertTrue(saved.isPresent());

        testRestTemplate.delete(url, addressUpdateDel);

        addressUpdateDel.setCity("NewMoskva");
        addressUpdateDel.setStreet("NewOlegova");
        addressUpdateDel.setZip("56643");

        ResponseEntity<Integer> update = testRestTemplate.withBasicAuth("admin@admin.cz", "heslo").postForEntity(url, addressUpdateDel, Integer.class);
        assertNotNull(update.getBody());
        assertEquals(HttpStatus.CREATED, update.getStatusCode());

        Optional<User> updatedResult = dao.findById(update.getBody());
        assertTrue(updatedResult.isPresent());

        assertEquals(addressUpdateDel.getCity(), updatedResult.get().getAddressDelivery().getCity());
        assertEquals(addressUpdateDel.getStreet(), updatedResult.get().getAddressDelivery().getStreet());
        assertEquals(addressUpdateDel.getZip(), updatedResult.get().getAddressDelivery().getZip());
    }
}
