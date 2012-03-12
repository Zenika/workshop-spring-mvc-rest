/**
 * 
 */
package com.zenika.nordnet;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.zenika.nordnet.model.Contact;
import com.zenika.nordnet.repository.ContactRepository;

/**
 * @author acogoluegnes
 *
 */
// TODO 01 enlever @Ignore du test
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-proxies.xml")
public class SpringProxiesTest {
	
	// TODO 02 lancer le test. Il échoue, car la méthode save() du repository n'est pas appelée au sein d'une transaction.

	@Autowired private ContactRepository targetRepo;
	
	@Autowired private PlatformTransactionManager tm;
	
	private ContactRepository proxyRepo;
	
	@Before public void setUp() {
		// TODO 03 créer un proxy qui gère les transactions autour du targetRepo
	}
	
	@Test public void springProxies() {
		// TODO 04 modifier le test pour utiliser proxyRepo au lieu de targetRepo et retester.
		long initialCount = targetRepo.count();
		Contact contact = new Contact();
		contact.setFirstname("Mickey");
		contact.setLastname("Mouse");
		targetRepo.save(contact);
		Assert.assertEquals(
			initialCount+1,
			targetRepo.count()
		);
	}
	
}
