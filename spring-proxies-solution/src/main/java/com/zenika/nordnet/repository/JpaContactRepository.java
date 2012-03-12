/**
 * 
 */
package com.zenika.nordnet.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.zenika.nordnet.model.Contact;


/**
 * @author acogoluegnes
 *
 */
@Repository
public class JpaContactRepository implements ContactRepository {
	
	@PersistenceContext EntityManager em;

	@Override
	public long count() {
		return ((Number) em.createQuery("select count(c) from Contact c").getSingleResult()).longValue();
	}
	
	@Override
	public Contact save(Contact contact) {
		em.persist(contact);
		return contact;
	}
	
}
