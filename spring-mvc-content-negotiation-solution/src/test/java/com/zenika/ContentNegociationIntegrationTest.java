/**
 * 
 */
package com.zenika;

import com.zenika.model.Contact;
import com.zenika.web.CsvHttpMessageConverter;
import com.zenika.web.LogClientHttpRequestInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentNegociationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContentNegociationIntegrationTest {
	
	RestTemplate tpl = new RestTemplate();
	
	String url = "http://localhost:{port}/";
	
	@Value("${local.server.port}") String port;
	
	@Before public void setUp() {
		url = url.replace("{port}", port);
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LogClientHttpRequestInterceptor());
		tpl.setInterceptors(interceptors);
	}
	
	@Test public void contentNegotiation() throws Exception {
		Contact[] contacts = tpl.getForObject(url+"contacts", Contact[].class);
		Long id = contacts[0].getId();
		Contact contact = tpl.getForObject(url+"contacts/{id}", Contact.class, id);
		Assert.assertEquals(id,contact.getId());
		
		List<HttpMessageConverter<?>> convs = new ArrayList<HttpMessageConverter<?>>();
		convs.add(new MappingJackson2HttpMessageConverter());
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
	
	

}
