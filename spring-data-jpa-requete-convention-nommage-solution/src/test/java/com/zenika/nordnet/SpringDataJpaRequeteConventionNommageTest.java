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
@ContextConfiguration("/spring-data-jpa-requete-convention-nommage.xml")
public class SpringDataJpaRequeteConventionNommageTest {

	@Autowired ContactRepository repo;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void findByLastname() {
		Assert.assertEquals(5,repo.findByLastname("Dalton").size());
	}
	
	@Test public void findByFirstnameAndLastname() {
		Assert.assertNotNull(repo.findByFirstnameAndLastname("Joe","Dalton"));
		Assert.assertNull(repo.findByFirstnameAndLastname("Bill","Dalton"));
	}
	
	@Test public void findByAddressCity() {
		Assert.assertEquals(7,repo.findByAddressCity("Los Angeles").size());
	}
	
	@Test public void findByAddressZipCode() {
		Assert.assertEquals(4,repo.findByAddressZipCode("90001").size());
	}
	
	@Test public void findByAddress_ZipCode() {
		Assert.assertEquals(4,repo.findByAddressZipCode("90001").size());
	}
	
	@Test public void findByAddressZipCodeLike() {
		Assert.assertEquals(7,repo.findByAddressZipCodeLike("9%").size());
	}
	
	@Test public void findByAddressZipCodeIn() {
		Assert.assertEquals(7,repo.findByAddressZipCodeIn("90001","91043").size());
	}
	
	@Test public void findByAgeGreaterThan() {
		Assert.assertEquals(5,repo.findByAgeGreaterThan(30).size());
	}
	
	@Test public void findByAgeBetween() {
		Assert.assertEquals(4,repo.findByAgeBetween(20,30).size());
	}
	

}
