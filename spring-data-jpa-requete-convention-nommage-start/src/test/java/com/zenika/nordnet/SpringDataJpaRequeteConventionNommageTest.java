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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-requete-convention-nommage.xml")
// TODO 01 décommenter @Ignore
@Ignore
public class SpringDataJpaRequeteConventionNommageTest {

	@Autowired ContactRepository repo;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	// TODO 03 tester la méthode de recherche par nom (chercher la famille Dalton)
	
	// TODO 05 tester la méthode de recherche par prénom et nom
	// tester une recherche avec un contact existant et un contact n'existant pas 
	
	// TODO 07 tester la méthode de recherche par ville (chercher les contacts de Los Angeles) 
	
	// TODO 09 tester la méthode de recherche par code postal
	
	// TODO 11 tester la méthode de recherche par code postal (nom sans ambiguité)
	
	// TODO 13 tester la méthode de recherche partielle par code postal
	// (chercher les contacts de Los Angeles, c'est-à-dire dont le code postal commence par 9)
	
	// TODO 15 tester la méthode de recherche par plusieurs codes postaux
	// (chercher les contacts de Los Angeles)
	
	// TODO 17 tester la méthode de recherche par âge supérieur à
	// (chercher les contacts ayant plus de 30 ans)
	
	// TODO 19 tester la méthode de recherche par une tranche d'âge
	// (chercher les contacts entre 20 et 30 ans)
	

}
