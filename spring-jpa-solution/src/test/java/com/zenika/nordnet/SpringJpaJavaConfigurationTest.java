/**
 * 
 */
package com.zenika.nordnet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.nordnet.model.Contact;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringJpaConfiguration.class)
public class SpringJpaJavaConfigurationTest {

	@Autowired private EntityManagerFactory emf;
	
	@Test public void springJpa() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			Contact contact = new Contact();
			contact.setFirstname("Mickey");
			contact.setLastname("Mouse");
			int initialCount = em.createQuery("from Contact").getResultList().size();
			em.persist(contact);
			Assert.assertEquals(
				initialCount+1,
				em.createQuery("from Contact").getResultList().size()
			);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
}
