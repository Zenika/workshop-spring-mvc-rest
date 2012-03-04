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

import com.zenika.nordnet.model.Contact;
import com.zenika.nordnet.repository.ContactRepository;

/**
 * @author acogoluegnes
 * 
 */
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

	@Test public void findByLastname() {
		Assert.assertEquals(5,contactRepository.findByLastname("Dalton").size());
	}
	
	
	@Test public void findByFirstnameAndLastname() {
		Assert.assertNotNull(contactRepository.findByFirstnameAndLastname("Joe","Dalton"));
		Assert.assertNull(contactRepository.findByFirstnameAndLastname("Bill","Dalton"));
	}
	
	@Test public void findByAgeGreaterThan() {
		Assert.assertEquals(5,contactRepository.findByAgeGreaterThan(30).size());
	}
	
	@Test public void setNewAddress() {
		Contact maDalton = contactRepository.findByFirstnameAndLastname("Ma", "Dalton");
		Contact joeDalton = contactRepository.findByFirstnameAndLastname("Joe", "Dalton");
		Assert.assertTrue(!maDalton.getAddress().getId().equals(joeDalton.getAddress().getId()));
		contactRepository.setNewAddress(maDalton.getAddress(), "Dalton");
		joeDalton = contactRepository.findByFirstnameAndLastname("Joe", "Dalton");
		Assert.assertTrue(maDalton.getAddress().getId().equals(joeDalton.getAddress().getId()));
	}
	
}
