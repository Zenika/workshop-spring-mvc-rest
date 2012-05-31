/**
 * 
 */
package com.zenika.cnamts;

import org.codehaus.jackson.JsonNode;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.web.client.RestTemplate;

import com.zenika.cnamts.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public class HateoasIntegrationTest {
	
	private static RestTemplate tpl = new RestTemplate();
	
	private final String BASE_URL = "http://localhost:8080/hateoas/zen-contact/";
	
	private static Server server;
	
	@BeforeClass public static void setUp() throws Exception {
		startServer();
	}
	
	@AfterClass public static void tearDown() throws Exception {
		server.stop();
	}
	
	@Test public void selectContacts() throws Exception {
		JsonNode nodes = tpl.getForObject(BASE_URL+"contacts", JsonNode.class);
		JsonNode node = nodes.get(0);
		JsonNode detailLink = node.get("id");
		String detailUrl = detailLink.get("href").getTextValue();
		
		Contact contact = tpl.getForObject(detailUrl,Contact.class);
		Assert.assertTrue(detailUrl.endsWith(contact.getId().toString()));
	}
	
	@Test public void selectContactsPages() throws Exception {
		String pageUrl = BASE_URL+"contacts/pages?page={page}&size={size}";
		String pageAsString = tpl.getForObject(
				pageUrl, 
				String.class,
				0,5
		);
		System.out.println(pageAsString);
	}
	
	private static void startServer() throws Exception {
		server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		String app = "hateoas";
		
		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/"+app);
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
	}

}
