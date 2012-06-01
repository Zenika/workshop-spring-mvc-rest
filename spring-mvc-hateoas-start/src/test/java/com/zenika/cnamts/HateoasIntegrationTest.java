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
		// TODO 01 lancer le test et vérifier qu'il fonctionne
		JsonNode nodes = tpl.getForObject(BASE_URL+"contacts", JsonNode.class);
		int totalElements = 12;
		Assert.assertEquals(totalElements,nodes.size());
		// TODO 02 écrire dans la console la variables nodes et analyser le contenu
		// l'idée du TP est que chaque contact contienne un lien vers son URL de détail
		
		// TODO 09 relancer le test et analyser le contenu de la réponse
		// le lien vers le détail du contact doit être présent pour chacun des éléments
		
		// les tâches suivantes sont à faire via l'API de Jackson (pas d'appel REST)
		// TODO 10 récupérer le premier contact à partir de la variable nodes

		// TODO 11 récupérer l'identifiant de la ressource (champ "id")

		// TODO 12 récupérer l'URL de détail (champ "href")
		String detailUrl = null;
		
		// TODO 13 décommenter les lignes suivantes, les analyser et lancer le test
		// Contact contact = tpl.getForObject(detailUrl,Contact.class);
		// Assert.assertTrue(detailUrl.endsWith(contact.getId().toString()));
	}
	
	@Test public void selectContactsPages() throws Exception {
		String pageUrl = BASE_URL+"contacts/pages?page={page}&size={size}";
		int pageSize = 5;
		// 1ère page
		JsonNode page = tpl.getForObject(
				pageUrl, 
				JsonNode.class,
				0,pageSize
		);
		Assert.assertEquals(page.get("content").size(), pageSize);
		int totalElements = 12;
		Assert.assertEquals(totalElements, page.get("totalElements").getIntValue());
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
				if(rel.equals(link.get("rel").getTextValue())) {
					return link.get("href").getTextValue();
				}
			}
			return null;
		}
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
