package com.zenika.web;

import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
// TODO 01 enlever l'annotation @Ignore
@Ignore
// TODO 02 mettre les annotations nécessaires à la configuration du test
public class ContactControllerTest {

    // TODO 03 analyser les propriétés nécessaires aux tests

    @Autowired
    WebApplicationContext ctx;

    @Autowired ContactRepository repo;

    MockMvc mockMvc;


    // TODO 04 initialiser MockMvc et le mock du repository


    // TODO 06 lancer le test pour vérifier que la configuration est correcte

    @Test
    public void contactExists() throws Exception {
        // TODO 07 écrire le test pour la méthode contact
        // on suppose que le contact demandé existe
        // (regarder la méthode correspondante dans le controleur)

        // TODO 08 lancer le test
    }

    @Test
    public void contactDoesNotExists() throws Exception {
        // TODO 09 écrire le test pour la méthode contact
        // on suppose que le contact n'existe pas
        // (regarder la méthode correspondante dans le controleur)

        // TODO 10 lancer le test
    }

    @Test public void contacts() throws Exception {
        when(repo.findAll()).thenReturn(Arrays.asList(
                new Contact(1L,"John","Doe",33),
                new Contact(2L,"Jane","Doe",30)
        ));
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[0].lastname").value("Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstname").value("Jane"))
                .andExpect(jsonPath("$[1].lastname").value("Doe"));
    }

    @Test public void create() throws Exception {
        // TODO 11 écrire le test pour la méthode create
        // (regarder la méthode correspondante dans le controleur)
        // bien tester que le controleur retourne le bon statut et le bon header
        // mais aussi que le document JSON envoyé est bien converti en objet Contact

        Contact toBeCreated = new Contact(1L,"John","Doe",33);

        String contactJson = "{\"firstname\":\"John\",\"lastname\":\"Doe\",\"age\":33}";


        // TODO 12 lancer le test
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


    // TODO 05 analyser la classe de configuration
    // cette classe est automatiquement utilisée lors du lancement du test
    // elle active le component scan pour créer le controleur
    // et utilise un mock pour le ContactRepository.


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
