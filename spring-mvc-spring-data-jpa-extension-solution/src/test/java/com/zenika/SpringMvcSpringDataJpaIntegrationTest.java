/**
 * 
 */
package com.zenika;

import junit.framework.Assert;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.zenika.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
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
	
	@Test public void selectPageableContacts() throws Exception {
		String pageUrl = url+"contacts?page={page}&page.size={size}&page.sort={sort}";
		Contact[] contacts = tpl.getForObject(
				pageUrl, 
				Contact[].class,
				0,5,"firstname"
		);
		Assert.assertEquals(5, contacts.length);
		Assert.assertEquals("Averell",contacts[0].getFirstname());
		contacts = tpl.getForObject(
				pageUrl, 
				Contact[].class,
				1,5,"firstname"
		);
		Assert.assertEquals(5, contacts.length);
		contacts = tpl.getForObject(
				pageUrl, 
				Contact[].class,
				2,5,"firstname"
		);
		Assert.assertEquals(2, contacts.length);
	}
	
	@Test public void selectContactsPages() throws Exception {
		String pageUrl = url+"contacts/pages?page={page}&page.size={size}&page.sort={sort}";
		String pageAsString = tpl.getForObject(
				pageUrl, 
				String.class,
				0,5,"firstname"
		);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode page = mapper.readTree(pageAsString);
		Assert.assertTrue(page.get("content").isArray());
		ArrayNode content = (ArrayNode) page.get("content");
		Assert.assertEquals(5,content.size());
		Assert.assertEquals(5,page.get("size").getIntValue());
		Assert.assertEquals(5,page.get("numberOfElements").getIntValue());
		Assert.assertEquals(0,page.get("number").getIntValue());
		Assert.assertEquals(12,page.get("totalElements").getIntValue());
		Assert.assertTrue(page.get("firstPage").getBooleanValue());
		Assert.assertFalse(page.get("lastPage").getBooleanValue());
		
		pageAsString = tpl.getForObject(
				pageUrl, 
				String.class,
				1,5,"firstname"
		);
		
		page = mapper.readTree(pageAsString);
		Assert.assertTrue(page.get("content").isArray());
		content = (ArrayNode) page.get("content");
		Assert.assertEquals(5,content.size());
		Assert.assertEquals(5,page.get("size").getIntValue());
		Assert.assertEquals(5,page.get("numberOfElements").getIntValue());
		Assert.assertEquals(1,page.get("number").getIntValue());
		Assert.assertEquals(12,page.get("totalElements").getIntValue());
		Assert.assertFalse(page.get("firstPage").getBooleanValue());
		Assert.assertFalse(page.get("lastPage").getBooleanValue());
		
		pageAsString = tpl.getForObject(
				pageUrl, 
				String.class,
				2,5,"firstname"
		);
		
		page = mapper.readTree(pageAsString);
		Assert.assertTrue(page.get("content").isArray());
		content = (ArrayNode) page.get("content");
		Assert.assertEquals(2,content.size());
		Assert.assertEquals(5,page.get("size").getIntValue());
		Assert.assertEquals(2,page.get("numberOfElements").getIntValue());
		Assert.assertEquals(2,page.get("number").getIntValue());
		Assert.assertEquals(12,page.get("totalElements").getIntValue());
		Assert.assertFalse(page.get("firstPage").getBooleanValue());
		Assert.assertTrue(page.get("lastPage").getBooleanValue());
	}
	
	@Test public void search() throws Exception {
		String pageUrl = url+"contacts?search={search}";
		Contact[] contacts = tpl.getForObject(
				pageUrl, 
				Contact[].class,
				"dalton"
		);
		Assert.assertEquals(5,contacts.length);		
		pageUrl = url+"contacts?page={page}&page.size={size}&page.sort={sort}&search={search}";
		contacts = tpl.getForObject(
				pageUrl, 
				Contact[].class,
				0,10,"firstname","dalton"
		);
		Assert.assertEquals(5, contacts.length);
		
		pageUrl = url+"contacts/pages?page={page}&page.size={size}&page.sort={sort}&search={search}";
		String pageAsString = tpl.getForObject(
				pageUrl, 
				String.class,
				0,10,"firstname","dalton"
		);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode page = mapper.readTree(pageAsString);
		Assert.assertTrue(page.get("content").isArray());
		ArrayNode content = (ArrayNode) page.get("content");
		Assert.assertEquals(5,content.size());
		Assert.assertEquals(10,page.get("size").getIntValue());
		Assert.assertEquals(5,page.get("numberOfElements").getIntValue());
		Assert.assertEquals(0,page.get("number").getIntValue());
		Assert.assertEquals(5,page.get("totalElements").getIntValue());
		Assert.assertTrue(page.get("firstPage").getBooleanValue());
		Assert.assertTrue(page.get("lastPage").getBooleanValue());
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
