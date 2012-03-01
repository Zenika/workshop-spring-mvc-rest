/**
 * 
 */
package com.zenika.nordnet;

import java.util.Iterator;

import javax.sql.DataSource;

import junit.framework.Assert;

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
@ContextConfiguration("/spring-data-jpa-crud.xml")
public class SpringDataJpaCrudTest {

	@Autowired ContactRepository repo;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void count() {
		Assert.assertEquals(24, repo.count());
	}
	
	@Test public void findAll() {
		Iterator<Contact> iterator = repo.findAll().iterator();
		int count = 0;
		while(iterator.hasNext()) {
			iterator.next();
			count++;
		}
		Assert.assertEquals(24, count);
	}
	
	@Test public void save() {
		long initialCount = repo.count();
		Contact contact = new Contact();
		contact.setFirstname("Mickey");
		contact.setLastname("Mouse");
		repo.save(contact);
		Assert.assertEquals(initialCount+1,repo.count());
	}
	
	@Test public void deleteAll() {
		repo.deleteAll();
		Assert.assertEquals(0,repo.count());
	}
	
	@Test public void deleteIterable() {
		repo.delete(repo.findAll());
		Assert.assertEquals(0,repo.count());
	}
	
	@Test public void findAllFindOneDelete() {
		Iterator<Contact> iterator = repo.findAll().iterator();
		while(iterator.hasNext()) {
			Contact contact = iterator.next();
			contact = repo.findOne(contact.getId());
			repo.delete(contact);
		}
		Assert.assertEquals(0,repo.count());
	}
	
	

}
