/**
 * 
 */
package com.zenika.nordnet;

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
		// TODO 05 récupérer la liste des contacts
		// (utiliser la propriété url pour le début du chemin)
		
		// TODO 06 vérifier que le nombre de contact est bon
		// (il y a 12 contacts, voir le fichier data.sql)
		
		// TODO 07 lancer le test
		
		// TODO 09 récupérer un contact par son identifiant
		// (prendre l'identifiant du premier contact du tableau de contacts)
		
		// TODO 10 vérifier que l'identifiant du contact récupéré est le bon
		
		// TODO 11 lancer le test
	}
	
	// TODO 13 enlever @Ignore de la méthode de test
	@Ignore
	@Test public void crud() throws Exception {
		int initialCount = tpl.getForObject(url+"contacts", Contact[].class).length;
		Contact contact = new Contact();
		contact.setFirstname("Oncle");
		contact.setLastname("Picsou");
		contact.setAge(100);
		
		// TODO 14 envoyer le contact ci-dessus pour création
		
		
		// TODO 15 vérifier qu'il y a bien un contact en plus
		// (récupérer la liste des contacts et vérifier le compte)
		
		// TODO 16 lancer le test
		
		// TODO 17 récupérer le contact nouvellement créé (grâche à son URI)
		Contact lookedUpContact = null;
		
		// TODO 18 vérifier que les propriétés du contact sont correctes
		// (comparer les propriétés de contact avec celles de lookedUpContact)
		
		// TODO 19 lancer le test
		
		// TODO 21 positionner le nouvel âge sur lookedUpContact et envoyer la mise à jour au serveur		
		int newAge = 90;
		
		// TODO 22 récupérer le contact pour s'assurer que le nouvel âge a bien été positionné

		
		// TODO 23 s'assurer que le compte de contacts est toujours bon (initialCount + 1)
		
		// TODO 24 lancer le test
		
		// TODO 26 supprimer le contact
		
		// TODO 27 s'assurer que le compte de contact est bon (revenu à initialCount)

		
		// TODO Bonus 02 tenter de récupérer le contact et vérifier que l'on récupère une 404
		
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
