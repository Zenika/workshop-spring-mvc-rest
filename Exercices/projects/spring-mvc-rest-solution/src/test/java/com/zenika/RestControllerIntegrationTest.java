/**
 * 
 */
package com.zenika;

import com.zenika.model.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * 
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerIntegrationTest {
	
	RestOperations tpl = new RestTemplate();
	
	String url = "http://localhost:{port}/";
	
	@Value("${local.server.port}") String port;
	
	@Before public void setUp() {
		url = url.replace("{port}", port);
	}
	
	@Test public void selectAndFindOne() throws Exception {
		Contact[] contacts = tpl.getForObject(url+"contacts", Contact[].class);
		Assert.assertEquals(12, contacts.length);
		Long id = contacts[0].getId();
		Contact contact = tpl.getForObject(url+"contacts/{id}", Contact.class,id);
		Assert.assertEquals(id,contact.getId());
	}
	
	@Test public void crud() throws Exception {
		int initialCount = tpl.getForObject(url+"contacts", Contact[].class).length;
		Contact contact = new Contact();
		contact.setFirstname("Oncle");
		contact.setLastname("Picsou");
		contact.setAge(100);
		URI location = tpl.postForLocation(url+"contacts", contact);
		int newCount = tpl.getForObject(url+"contacts", Contact[].class).length;
		Assert.assertEquals(initialCount+1,newCount);
		
		Contact lookedUpContact = tpl.getForObject(location, Contact.class);
		Assert.assertEquals(contact.getFirstname(),lookedUpContact.getFirstname());
		Assert.assertEquals(contact.getLastname(),lookedUpContact.getLastname());
		Assert.assertEquals(contact.getAge(),lookedUpContact.getAge());
		
		int newAge = 90;
		lookedUpContact.setAge(newAge);
		tpl.put(location, lookedUpContact);
		lookedUpContact = tpl.getForObject(location, Contact.class);
		Assert.assertEquals(newAge,lookedUpContact.getAge());
		
		newCount = tpl.getForObject(url+"contacts", Contact[].class).length;
		Assert.assertEquals(initialCount+1,newCount);
		
		tpl.delete(location);
		
		newCount = tpl.getForObject(url+"contacts", Contact[].class).length;
		Assert.assertEquals(initialCount,newCount);
		
		try {
			contact = tpl.getForObject(location, Contact.class);
			Assert.fail("La ressource n'existe plus, une erreur 404 aurait du être retournée");
		} catch (HttpStatusCodeException e) {
			Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

}
