/**
 * 
 */
package com.zenika.nordnet;

import java.net.URI;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public class RestControllerIntegrationTest {
	
	private RestTemplate tpl = new RestTemplate();
	
	private final String url = "http://localhost:8080/crud-rest/zen-contact/";
	
	private static Server server;
	
	@BeforeClass public static void setUp() throws Exception {
		startServer();        
	}
	
	@AfterClass public static void tearDown() throws Exception {
		server.stop();
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

	private static void startServer() throws Exception {
		server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		String app = "crud-rest";
		
		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/"+app);
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
	}

}
