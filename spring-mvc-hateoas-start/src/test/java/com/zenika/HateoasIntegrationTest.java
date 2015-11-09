/**
 * 
 */
package com.zenika;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.zenika.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(HateoasApplication.class)
@WebIntegrationTest(randomPort=true)
public class HateoasIntegrationTest {
	
	RestTemplate tpl = new RestTemplate();
	
	String url = "http://localhost:{port}/";
	
	@Value("${local.server.port}") String port;
	
	@Before public void setUp() {
		url = url.replace("{port}", port);
	}
	
	@Test public void selectContacts() throws Exception {
		// TODO 01 lancer le test et vérifier qu'il fonctionne
		JsonNode nodes = tpl.getForObject(url+"contacts", JsonNode.class);
		int totalElements = 12;
		Assert.assertEquals(totalElements,nodes.size());
		// TODO 02 écrire dans la console la variables nodes et analyser le contenu
		// l'idée du TP est que chaque contact contienne un lien vers son URL de détail
		
		// TODO 09 relancer le test et analyser le contenu de la réponse
		// le lien vers le détail du contact doit être présent pour chacun des éléments
		
		// les tâches suivantes sont à faire via l'API de Jackson (pas d'appel REST)
		// TODO 10 récupérer le premier contact à partir de la variable nodes

		// TODO 11 récupérer le premier et unique lien (champ "links") 

		// TODO 12 récupérer l'URL de détail (champ "href")
		String detailUrl = null;
		
		// TODO 13 décommenter les lignes suivantes, les analyser et lancer le test
		// Contact contact = tpl.getForObject(detailUrl,Contact.class);
		// Assert.assertTrue(detailUrl.endsWith(contact.getId().toString()));
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
		int totalElements = 12;
		Assert.assertEquals(totalElements, page.get("totalElements").asInt());
		// TODO 15 afficher la page dans la console, lancer le test et analyser la sortie console
		// pour l'instant, une page ne contient pas de lien vers les autres pages
		
		// TODO 22 relancer le test pour ce qui s'affiche dans la console
		
		// TODO 23 analyser la méthode getLink (elle récupère les liens selon la valeur de rel)
		// TODO 24 vérifier que le lien PREVIOUS est nul
		
		// TODO 25 récupérer l'URL de la page suivante (lien NEXT)

		// 2ème page
		// TODO 26 récupérer la 2ème page sous forme de JsonNode
		
		// TODO 27 vérifier que le nombre d'éléments de la page est correct
		
		// TODO 28 récupérer l'URL de la page suivante
		
		// 3ème page
		// TODO 29 récupérer la 3ème page sous forme de JsonNode
		
		// TODO 30 vérifier que le nombre d'éléments de la page est correct
		// (attention, c'est la dernière page et elle n'est pas complète)
		// (on peut utiliser le nombre total d'éléments et l'opérateur modulo %)
	
		// TODO 31 vérifier que cette page n'a pas de lien suivant
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
