/**
 * 
 */
package com.zenika.nordnet;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.web.client.RestTemplate;

import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
@Ignore
public class SpringMvcSpringDataJpaIntegrationTest {
	
	private RestTemplate tpl = new RestTemplate();
	
	private final String url = "http://localhost:8080/spring-data-jpa/zen-contact/";
	
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
	
	private static void startServer() throws Exception {
		server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		String app = "spring-data-jpa";
		
		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/"+app);
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
	}

}
