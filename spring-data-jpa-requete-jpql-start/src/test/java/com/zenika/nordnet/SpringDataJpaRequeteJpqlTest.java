/**
 * 
 */
package com.zenika.nordnet;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.nordnet.repository.ContactRepository;

/**
 * @author acogoluegnes
 * 
 */
// TODO 02 enlever l'annotation @Ignore du test
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-requete-jpql.xml")
public class SpringDataJpaRequeteJpqlTest {

	@Autowired ContactRepository contactRepository;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	// TODO 03 tester findByLastname, bien vérifier qu'une seule requête SQL est exécutée
	
	
	// TODO 05 tester findByFirstnameAndLastname, bien vérifier qu'une seule requête SQL est exécutée
	
	
	// TODO 07 tester findByAgeGreaterThan, bien vérifier qu'une seule requête SQL est exécutée


	// TODO 09 tester setNewAddress
	// cas de test : les frères Dalton retournent habiter chez leur mère !
	
}
