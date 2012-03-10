/**
 * 
 */
package com.zenika.nordnet;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.zenika.nordnet.model.Contact;
import com.zenika.nordnet.web.CsvHttpMessageConverter;
import com.zenika.nordnet.web.LogClientHttpRequestInterceptor;

/**
 * 
 * @author acogoluegnes
 *
 */
public class ContentNegociationIntegrationTest {
	
	private static RestTemplate tpl = new RestTemplate();
	
	private final String url = "http://localhost:8080/content-negotiation/zen-contact/";
	
	private static Server server;
	
	@BeforeClass public static void setUp() throws Exception {
		startServer();
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LogClientHttpRequestInterceptor());
		tpl.setInterceptors(interceptors);
	}
	
	@AfterClass public static void tearDown() throws Exception {
		server.stop();
	}
	
	@Test public void contentNegotiation() throws Exception {
		Contact[] contacts = tpl.getForObject(url+"contacts", Contact[].class);
		Long id = contacts[0].getId();
		Contact contact = tpl.getForObject(url+"contacts/{id}", Contact.class, id);
		Assert.assertEquals(id,contact.getId());
		
		List<HttpMessageConverter<?>> convs = new ArrayList<HttpMessageConverter<?>>();
		convs.add(new MappingJacksonHttpMessageConverter());
		tpl.setMessageConverters(convs);
		ResponseEntity<Contact> entity = tpl.getForEntity(url+"contacts/{id}", Contact.class,id);
		Assert.assertTrue(MediaType.APPLICATION_JSON.includes(entity.getHeaders().getContentType()));
		
		convs.clear();
		convs.add(new Jaxb2RootElementHttpMessageConverter());
		tpl.setMessageConverters(convs);
		entity = tpl.getForEntity(url+"contacts/{id}", Contact.class,id);
		Assert.assertTrue(MediaType.APPLICATION_XML.includes(entity.getHeaders().getContentType()));
		
		convs.clear();
		convs.add(new CsvHttpMessageConverter());
		tpl.setMessageConverters(convs);
		entity = tpl.getForEntity(url+"contacts/{id}", Contact.class,id);
		Assert.assertTrue(CsvHttpMessageConverter.CSV_MEDIA_TYPE.includes(entity.getHeaders().getContentType()));
	}
	
	private static void startServer() throws Exception {
		server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		String app = "content-negotiation";
		
		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/"+app);
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
	}

}
