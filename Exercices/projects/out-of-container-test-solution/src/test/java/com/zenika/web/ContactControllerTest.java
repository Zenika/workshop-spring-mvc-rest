package com.zenika.web;

import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 *
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class ContactControllerTest {

    @Autowired
    WebApplicationContext ctx;

    @Autowired ContactRepository repo;

    MockMvc mockMvc;

    @Before public void setUp() {
        this.mockMvc = webAppContextSetup(ctx).build();
        reset(repo);
    }

    @Test
    public void contactExists() throws Exception {
        Long id = 1L;
        when(repo.findOne(id)).thenReturn(new Contact(id,"John","Doe",33));
        mockMvc.perform(get("/contacts/{id}", id))
//            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("firstname").value("John"))
            .andExpect(jsonPath("lastname").value("Doe"))
            .andExpect(jsonPath("age").value(33));
    }

    @Test
    public void contactDoesNotExists() throws Exception {
        Long id = 1L;
        when(repo.findOne(1L)).thenReturn(null);
        mockMvc.perform(get("/contacts/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test public void contacts() throws Exception {
        when(repo.findAll()).thenReturn(Arrays.asList(
                new Contact(1L,"John","Doe",33),
                new Contact(2L,"Jane","Doe",30)
        ));
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
//                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[0].lastname").value("Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstname").value("Jane"))
                .andExpect(jsonPath("$[1].lastname").value("Doe"));
    }

    @Test public void create() throws Exception {
        Contact toBeCreated = new Contact(1L,"John","Doe",33);

        when(repo.save(any(Contact.class))).thenReturn(toBeCreated);
        mockMvc.perform(post("/contacts")
                    .content("{\"firstname\":\"John\",\"lastname\":\"Doe\",\"age\":33}")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location","http://localhost/contacts/1"));

        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(repo).save(contactCaptor.capture());
        Contact captured = contactCaptor.getValue();
        Assert.assertEquals(toBeCreated.getFirstname(),captured.getFirstname());
        Assert.assertEquals(toBeCreated.getLastname(),captured.getLastname());
        Assert.assertEquals(toBeCreated.getAge(),captured.getAge());
    }

    @Test public void update() throws Exception {
        Contact toBeUpdated = new Contact(1L,"John","Doe",33);

        when(repo.save(any(Contact.class))).thenReturn(toBeUpdated);
        mockMvc.perform(put("/contacts/{id}",toBeUpdated.getId())
                .content("{\"firstname\":\"John\",\"lastname\":\"Doe\",\"age\":33}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(repo).save(contactCaptor.capture());
        Contact captured = contactCaptor.getValue();
        Assert.assertEquals(toBeUpdated.getFirstname(),captured.getFirstname());
        Assert.assertEquals(toBeUpdated.getLastname(),captured.getLastname());
        Assert.assertEquals(toBeUpdated.getAge(),captured.getAge());
    }

    @Test public void deleteContact() throws Exception {
        mockMvc.perform(delete("/contacts/{id}",1L))
            .andExpect(status().isNoContent());

        verify(repo).delete(1L);
    }


    @Configuration
    @EnableWebMvc
    @ComponentScan(basePackageClasses = ContactController.class)
    public static class TestConfiguration {

        @Bean
        public ContactRepository contactRepository() {
            return mock(ContactRepository.class);
        }

    }


}
