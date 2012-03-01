/**
 * 
 */
package com.zenika.nordnet;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-crud.xml")
public class SpringDataJpaCrudTest {

	// TODO 02 injecter le repository
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		// TODO 03 analyser le code d'initialisation
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		// TODO 04 regarder le contenu du fichier de données
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void count() {
		// TODO 05 implémenter le test de count
	}
	
	@Test public void findAll() {
		// TODO 06 implémenter le test de findAll
		// se contenter de vérifier le nombre d'enregistrements retournés
	}
	
	@Test public void save() {
		// TODO 07 implémenter le test de save
	}
	
	@Test public void deleteAll() {
		// TODO 08 implémenter le test de deleteAll
	}
	
	@Test public void deleteIterable() {
		// TODO 09 implémenter le test pour supprimer avec un Iterable
	}
	
	@Test public void findAllFindOneDelete() {
		// TODO 10 implémenter le test findAllFindOneDelete
		// récupérer tous les enregistrements (findAll)
		// itérer dessus et les charger un par un (findOne) pour les effacer un par un (delete)
	}
	
	

}
