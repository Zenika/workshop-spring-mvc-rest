/**
 * 
 */
package com.zenika.nordnet;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.zenika.nordnet.model.Contact;

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
		// TODO 02 ajouter LogClientHttpRequestInterceptor sur le RestTemplate
		
		// TODO 03 lancer le test (le header Accept doit apparaître dans la console) 
	}
	
	@AfterClass public static void tearDown() throws Exception {
		server.stop();
	}
	
	@Test public void contentNegotiation() throws Exception {
		Contact[] contacts = tpl.getForObject(url+"contacts", Contact[].class);
		
		// TODO 01 lancer le test (il doit fonctionner)
		
		// TODO 06 récupérer le premier contact de la liste (via son ID)
		
		// TODO 07 vérifier que l'ID du Contact retourné est le bon
		
		// TODO 08 lancer le test et regarder ce que "comprend" le client
		
		List<HttpMessageConverter<?>> convs = new ArrayList<HttpMessageConverter<?>>();
		// TODO 09 positionner le MappingJacksonHttpMessageConverter sur le RestTemplate 
		// (ce doit être le seul convertisseur)
		
		// TODO 10 récupérer le contact sous forme d'une ResponseEntity
		
		// TODO 11 vérifier que le contenu est bien du JSON
		
		// TODO 12 lancer le test (il doit fonctionner)
		
		convs.clear();
		// TODO 13 positionner le MappingJacksonHttpMessageConverter sur le RestTemplate 
		// (ce doit être le seul convertisseur)
		
		// TODO 14 récupérer le contact sous forme d'une ResponseEntity
		
		// TODO 15 lancer le test (il doit échouer : le serveur n'est pas capable de sérialiser un contact en XML)
		
		
		// TODO 17 vérifier que le contenu est bien du XML
		
		// TODO 18 lancer le test (il doit fonctionner)
		
		convs.clear();
		// TODO 20 positionner le CsvHttpMessageConverter sur le RestTemplate 
		// (ce doit être le seul convertisseur)
		
		// TODO 21 récupérer le contact sous forme d'une ResponseEntity
		
		// TODO 22 vérifier que le contenu est bien du JSON
			
		// TODO 23 lancer le test (il doit fonctionner)
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
