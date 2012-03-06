/**
 * 
 */
package com.zenika.nordnet;

import javax.sql.DataSource;


import static com.zenika.nordnet.repository.ContactSpecs.*;
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
@ContextConfiguration("/spring-data-jpa-query-dsl.xml")
public class SpringDataJpaQueryDslTest {

	@Autowired ContactRepository repo;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void findOutlawsInMidThirties() {
		Assert.assertEquals(4,repo.findOutlawsInMidThirties().size());
	}
	
	@Test public void predicates() {
		Assert.assertEquals(5,repo.count(outlaws()));
		Assert.assertEquals(6,repo.count(inMidThirties()));
		Assert.assertEquals(4,repo.count(outlaws().and(inMidThirties())));
	}
	
}
