/**
 * 
 */
package com.zenika.nordnet;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
@ContextConfiguration("/spring-data-jpa-repository-custom.xml")
public class SpringDataJpaRepositoryCustomTest {

	@Autowired ContactRepository contactRepository;
	
	// TODO 10 injecter le AddressRepository
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void findByLastname() {
		Assert.assertEquals(5,contactRepository.findByLastname("Dalton").size());
	}
	
	// TODO 05 tester ContactRepository.findByExample
	// 2 cas de tests : un résultat trouvé et pas de résultat trouvé
	
	
	// TODO 11 tester AddressRepository.findByExample
	// 2 cas de tests : un résultat trouvé et pas de résultat trouvé
	
}
