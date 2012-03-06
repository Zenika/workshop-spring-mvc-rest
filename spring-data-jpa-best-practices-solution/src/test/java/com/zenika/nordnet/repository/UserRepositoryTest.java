/**
 * 
 */
package com.zenika.nordnet.repository;



import static com.zenika.nordnet.repository.UserSpecs.*;
import static org.springframework.data.jpa.domain.Specifications.*;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.nordnet.repository.UserRepository;

/**
 * @author acogoluegnes
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-best-practices.xml")
public class UserRepositoryTest {

	@Autowired UserRepository repo;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void findUserByLoginActivated() {
		Assert.assertNotNull(repo.findOne(where(loginIs("user1")).and(isActivated())));
	}
	
	@Test public void findUserByLoginActivatedBadLogin() {
		Assert.assertNull(repo.findOne(where(loginIs("userxxx")).and(isActivated())));
	}
	
	@Test public void findUserByLoginActivatedNotActivated() {
		Assert.assertNull(repo.findOne(where(loginIs("user3")).and(isActivated())));
	}
	
	@Test public void findUserByLoginActivatedMultipleLigin() {
		try {
			repo.findOne(where(loginIs("user2")).and(isActivated()));
			Assert.fail("2 rows with the same login, both activated. Should have thrown an exception.");
		} catch(IncorrectResultSizeDataAccessException e) {
			// OK
		}
	}
	
}
