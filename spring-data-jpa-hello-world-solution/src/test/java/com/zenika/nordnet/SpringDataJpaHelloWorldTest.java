/**
 * 
 */
package com.zenika.nordnet;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.nordnet.model.Contact;
import com.zenika.nordnet.repository.ContactRepository;

/**
 * @author acogoluegnes
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-hello-world.xml")
public class SpringDataJpaHelloWorldTest {

	@Autowired ContactRepository repo;

	@Test public void springJpa() {
		Contact contact = new Contact();
		contact.setFirstname("Mickey");
		contact.setLastname("Mouse");
		long initialCount = repo.count();
		repo.save(contact);
		Assert.assertEquals(initialCount + 1, repo.count());
	}

}
