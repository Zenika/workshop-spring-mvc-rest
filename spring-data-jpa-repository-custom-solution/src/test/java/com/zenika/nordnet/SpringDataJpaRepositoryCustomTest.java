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

import com.zenika.nordnet.model.Address;
import com.zenika.nordnet.model.Contact;
import com.zenika.nordnet.repository.AddressRepository;
import com.zenika.nordnet.repository.ContactRepository;

/**
 * @author acogoluegnes
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-repository-custom.xml")
public class SpringDataJpaRepositoryCustomTest {

	@Autowired ContactRepository contactRepository;
	
	@Autowired AddressRepository addressRepository;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void findByLastname() {
		Assert.assertEquals(5,contactRepository.findByLastname("Dalton").size());
	}
	
	@Test public void findContactByExample() {
		Contact contact = new Contact();
		contact.setFirstname("Joe");
		contact.setLastname("Dalton");
		Assert.assertEquals(1,contactRepository.findByExample(contact).size());
		contact.setFirstname("Bill");
		Assert.assertEquals(0,contactRepository.findByExample(contact).size());
	}
	
	@Test public void findAddressByExample() {
		Address address = new Address();
		address.setZipCode("90001");
		address.setCity("Los Angeles");
		Assert.assertEquals(1,addressRepository.findByExample(address).size());
		address.setZipCode("95000");
		Assert.assertEquals(0,addressRepository.findByExample(address).size());
	}

}
