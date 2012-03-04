/**
 * 
 */
package com.zenika.nordnet;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
@ContextConfiguration("/spring-data-jpa-tri-pagination.xml")
public class SpringDataJpaTriPaginationTest {

	@Autowired ContactRepository repo;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void findByLastname() {
		List<Contact> contacts = repo.findByLastname("Dalton",new Sort(Direction.DESC,"firstname"));
		String [] expected = new String[]{"William","Ma","Joe","Jack","Averell"};
		Assert.assertEquals(expected.length,contacts.size());
		for(int i=0;i<expected.length;i++) {
			Assert.assertEquals(expected[i],contacts.get(i).getFirstname());
		}
	}
	
	@Test public void findByCitySort() {
		List<Contact> contacts = repo.findByCity("Coffeyville",new Sort(Direction.DESC,"firstname"));
		String [] expected = new String[]{"William","Joe","Jack","Averell"};
		Assert.assertEquals(expected.length,contacts.size());
		for(int i=0;i<expected.length;i++) {
			Assert.assertEquals(expected[i],contacts.get(i).getFirstname());
		}
	}
	
	@Test public void findByCityPageable() {
		String [] expected = new String [] {"Riri","Minnie","Mickey","Loulou","Fifi","Donald","Daisy"};
		PageRequest pageable = new PageRequest(0, 5,new Sort(Direction.DESC,"firstname"));
		List<Contact> contacts = repo.findByCity("Los Angeles",pageable);
		Assert.assertEquals(pageable.getPageSize(),contacts.size());
		checkPage(expected, contacts,pageable.getPageNumber(),pageable.getPageSize());
		
		pageable = new PageRequest(1, 5,new Sort(Direction.DESC,"firstname"));
		contacts = repo.findByCity("Los Angeles",pageable);
		checkPage(expected, contacts,pageable.getPageNumber(),pageable.getPageSize());
	}
	
	@Test public void findPageByCity() {
		String [] expected = new String [] {"Riri","Minnie","Mickey","Loulou","Fifi","Donald","Daisy"};
		PageRequest pageable = new PageRequest(0, 5,new Sort(Direction.DESC,"firstname"));
		Page<Contact> page = repo.findPageByCity("Los Angeles",pageable);
		Assert.assertEquals(7,page.getTotalElements());
		Assert.assertEquals(2,page.getTotalPages());
		
		Assert.assertEquals(pageable.getPageSize(),page.getContent().size());
		checkPage(expected, page.getContent(),pageable.getPageNumber(),pageable.getPageSize());
		
		pageable = new PageRequest(1, 5,new Sort(Direction.DESC,"firstname"));
		page = repo.findPageByCity("Los Angeles",pageable);
		checkPage(expected, page.getContent(),pageable.getPageNumber(),pageable.getPageSize());
	}

	private void checkPage(String[] data, List<Contact> page,int pageNumber, int pageSize) {
		int count = 0;
		for(Contact contact : page) {
			Assert.assertEquals(data[pageNumber*pageSize+count],contact.getFirstname());
			count++;
		}
	}
	
}
