package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Address;
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

    @Test
    public void updateUserTest(){
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

        usrCntrl.deleteId(response.getBody().getId());

        assertNull(usrCntrl.getId(response.getBody().getId()));
    }

    @Test
    public void createBillingAdressTest(){
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

        Address addr = new Address();
        addr.setCity("Praha");
        addr.setStreet("Na Lysine");
        addr.setZip("15200");

        usrCntrl.createAddressBillingId(response.getBody().getId() , addr);

        assertNotNull(response.getBody().getAddressBilling());
    }

    @Test
    public void createDeliverAdressTest(){
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

        Address addr = new Address();
        addr.setCity("Praha");
        addr.setStreet("Na Lysine");
        addr.setZip("15200");

        usrCntrl.createAddressDeliveryId(response.getBody().getId() , addr);

        assertNotNull(response.getBody().getAddressDelivery());
    }

    @Test
    public void updateBillingAddressTest(){
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

}
