/**
 * 
 */
package com.zenika;


import com.fasterxml.jackson.databind.JsonNode;
import com.zenika.model.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HateoasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HateoasIntegrationTest {
	
	RestTemplate tpl = new RestTemplate();
	
	String url = "http://localhost:{port}/";
	
	@Value("${local.server.port}") String port;
	
	@Before public void setUp() {
		url = url.replace("{port}", port);
	}
	
	@Test public void selectContacts() throws Exception {
		JsonNode nodes = tpl.getForObject(url+"contacts", JsonNode.class);
		int totalElements = 12;
		Assert.assertEquals(totalElements,nodes.size());
		JsonNode firstNode = nodes.get(0);
		JsonNode detailLink = firstNode.get("links").get(0);
		String detailUrl = detailLink.get("href").asText();
		
		Contact contact = tpl.getForObject(detailUrl,Contact.class);
		Assert.assertTrue(detailUrl.endsWith(contact.getId().toString()));
	}
	
	@Test public void selectContactsPages() throws Exception {
		String pageUrl = url+"contacts/pages?page={page}&size={size}";
		int pageSize = 5;
		// 1ère page
		JsonNode page = tpl.getForObject(
				pageUrl, 
				JsonNode.class,
				0,pageSize
		);
		Assert.assertEquals(page.get("content").size(), pageSize);
		int totalElements = page.get("totalElements").asInt();
		Assert.assertNull(getLink(page,Link.REL_PREVIOUS));
		pageUrl = getLink(page,Link.REL_NEXT);
		Assert.assertNotNull(pageUrl);
		// 2ème page
		page = tpl.getForObject(pageUrl, JsonNode.class);
		Assert.assertEquals(page.get("content").size(), pageSize);
		pageUrl = getLink(page,Link.REL_NEXT);
		// 3ème page
		page = tpl.getForObject(pageUrl, JsonNode.class);
		Assert.assertEquals(page.get("content").size(), totalElements%pageSize);
		Assert.assertNull(getLink(page,Link.REL_NEXT));
	}
	
	private String getLink(JsonNode node,String rel) {
		/* structure d'une page :
		 { "links":[
           {"rel":"next","href":"http://localhost:8080/hateoas/zen-contact/contacts/pages?page=1&size=5"},
           {"rel":"first","href":"http://localhost:8080/hateoas/zen-contact/contacts/pages?page=0&size=5"},
           {"rel":"last","href":"http://localhost:8080/hateoas/zen-contact/contacts/pages?page=2&size=5"},
           {"rel":"self","href":"http://localhost:8080/hateoas/zen-contact/contacts/pages?page=0&size=5"}
           ],
           "content":[ ... ]
         }
		*/
		JsonNode links = node.get("links");
		if(links == null) {
			return null;
		} else {
			for(JsonNode link : links) {
				if(rel.equals(link.get("rel").asText())) {
					return link.get("href").asText();
				}
			}
			return null;
		}
	}
	
}
