package com.quetinkee.eshop.controllers;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.TestCase.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "/test.properties")   // add custom application settings
public class ProfileontrollerIntegrationMockTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserDao dao;

    @Test
    public void registerNewUserTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);

        User newUser = new User();
        newUser.setFirstName("Zhigalo");
        newUser.setLastName("Ebanoye");
        newUser.setMail("ebazhi@yobaa.com");
        newUser.setPassword("hersosi2002");
        newUser.setPhone("123456789");
        newUser.setAddressDelivery(new Address("Street", "City", "12345"));

        MvcResult result = mvc.perform(post("/api/profile")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newUser)))
            .andExpect(status().isCreated())
            .andReturn();

        Optional<User> saved = dao.findById(Integer.parseInt(result.getResponse().getContentAsString()));
        assertTrue(saved.isPresent());

        assertNotNull(saved.get().getId());
        assertEquals("Zhigalo" , saved.get().getFirstName());
        assertEquals("Ebanoye" , saved.get().getLastName());
        assertEquals("ebazhi@yobaa.com" , saved.get().getMail());
        assertEquals("123456789" , saved.get().getPhone());
    }

    @Test
    public void getCurrentUserInfoTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/profile")
            .accept(MediaType.APPLICATION_JSON)
            .with(httpBasic("admin@admin.cz","heslo")))
        .andExpect(status().isOk())
            .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
    }
}
