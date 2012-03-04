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
// TODO 02 enlever @Ignore du test
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-tri-pagination.xml")
public class SpringDataJpaTriPaginationTest {

	@Autowired ContactRepository repo;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	// TODO 03 tester findByLastname
	// test : tri descendant sur les prénoms, récupérer la famille Dalton et vérifier qu'ils sont dans le bon ordre
	
	
	// TODO 05 tester findByCity(String,Sort)
	// test : tri descendant sur les prénoms, récupérer les frères Dalton et vérifier qu'ils sont dans le bon ordre
	// (les frères Dalton habite à Coffeyville)
	
	// TODO 07 tester findByCity(String,Pageable)
	// test : tri descendant sur les prénoms, page de 5 éléments, récupérer les habitants de Los Angeles
	// vérifier que les contacts sont dans le bon ordre pour les 2 pages obtenues

	// TODO 10 tester findPageByCity(String,Pageable)
	// test : tri descendant sur les prénoms, page de 5 éléments, récupérer les habitants de Los Angeles
	// vérifier que les contacts sont dans le bon ordre pour les 2 pages obtenues
	
}
