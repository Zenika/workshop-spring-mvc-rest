/**
 * 
 */
package com.zenika;

import com.zenika.model.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
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
		
		// TODO 02 ajouter LogClientHttpRequestInterceptor sur le RestTemplate

		// TODO 03 lancer le test (le header Accept doit apparaître dans la
		// console)
	}

	@Test
	public void contentNegotiation() throws Exception {
		Contact[] contacts = tpl
				.getForObject(url + "contacts", Contact[].class);

		// TODO 01 lancer le test (il doit fonctionner)

		// TODO 06 récupérer le premier contact de la liste (via son ID)

		// TODO 07 vérifier que l'ID du Contact retourné est le bon

		// TODO 08 lancer le test et regarder ce que "comprend" le client

		List<HttpMessageConverter<?>> convs = new ArrayList<HttpMessageConverter<?>>();
		// TODO 09 positionner le MappingJacksonHttpMessageConverter sur le
		// RestTemplate
		// (ce doit être le seul convertisseur)

		// TODO 10 récupérer le contact sous forme d'une ResponseEntity

		// TODO 11 vérifier que le contenu est bien du JSON

		// TODO 12 lancer le test (il doit fonctionner)

		convs.clear();
		// TODO 13 positionner le Jaxb2RootElementHttpMessageConverter sur le
		// RestTemplate
		// (ce doit être le seul convertisseur)

		// TODO 14 récupérer le contact sous forme d'une ResponseEntity

		// TODO 15 lancer le test (il doit échouer : le serveur n'est pas
		// capable de sérialiser un contact en XML)

		// TODO 17 vérifier que le contenu est bien du XML

		// TODO 18 lancer le test (il doit fonctionner)

		convs.clear();
		// TODO 20 positionner le CsvHttpMessageConverter sur le RestTemplate
		// (ce doit être le seul convertisseur)

		// TODO 21 récupérer le contact sous forme d'une ResponseEntity

		// TODO 22 vérifier que le contenu est bien du CSV

		// TODO 23 lancer le test (il doit fonctionner)
	}

}
