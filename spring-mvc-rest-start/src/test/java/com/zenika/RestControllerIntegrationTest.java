/**
 * 
 */
package com.zenika;


import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.zenika.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RestApplication.class)
@WebIntegrationTest(randomPort=true)
public class RestControllerIntegrationTest {
	
	RestOperations tpl = new RestTemplate();
	
	String url = "http://localhost:{port}/";
	
	@Value("${local.server.port}") String port;
	
	@Before public void setUp() {
		url = url.replace("{port}", port);
	}
	
	@Test public void selectAndFindOne() throws Exception {
		// TODO 04 récupérer la liste des contacts
		// (utiliser la propriété url pour le début du chemin)
		
		// TODO 05 vérifier que le nombre de contact est bon
		// (il y a 12 contacts, voir le fichier data.sql)
		
		// TODO 06 lancer le test (il doit passer)
		
		// TODO 08 récupérer un contact par son identifiant
		// (prendre l'identifiant du premier contact du tableau de contacts)
		
		// TODO 09 vérifier que l'identifiant du contact récupéré est le bon
		
		// TODO 10 lancer le test (il doit passer)
	}
	
	// TODO 12 enlever @Ignore de la méthode de test
	@Ignore
	@Test public void crud() throws Exception {
		int initialCount = tpl.getForObject(url+"contacts", Contact[].class).length;
		Contact contact = new Contact();
		contact.setFirstname("Oncle");
		contact.setLastname("Picsou");
		contact.setAge(100);
		
		// TODO 13 envoyer le contact ci-dessus pour création
		
		
		// TODO 14 vérifier qu'il y a bien un contact en plus
		// (récupérer la liste des contacts et vérifier le compte)
		
		// TODO 15 lancer le test (il doit passer)
		
		// TODO 16 récupérer le contact nouvellement créé (grâche à son URI)
		Contact lookedUpContact = null;
		
		// TODO 17 vérifier que les propriétés du contact sont correctes
		// (comparer les propriétés de contact avec celles de lookedUpContact)
		
		// TODO 18 lancer le test
		
		// TODO 20 positionner le nouvel âge sur lookedUpContact et envoyer la mise à jour au serveur		
		int newAge = 90;
		
		// TODO 21 récupérer le contact pour s'assurer que le nouvel âge a bien été positionné

		
		// TODO 22 s'assurer que le compte de contacts est toujours bon (initialCount + 1)
		
		// TODO 23 lancer le test
		
		// TODO 25 supprimer le contact
		
		// TODO 26 s'assurer que le compte de contact est bon (revenu à initialCount)

		
		// TODO Bonus 02 tenter de récupérer le contact et vérifier que l'on récupère une 404
		
	}

}
