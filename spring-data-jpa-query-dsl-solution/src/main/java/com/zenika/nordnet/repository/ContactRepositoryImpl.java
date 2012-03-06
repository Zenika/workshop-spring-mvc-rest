/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPAQuery;
import com.zenika.nordnet.model.Contact;
import com.zenika.nordnet.model.QContact;

/**
 * @author acogoluegnes
 *
 */
public class ContactRepositoryImpl implements ContactRepositoryCustom {
	
	@PersistenceContext EntityManager em;
	
	@Override
	public List<Contact> findOutlawsInMidThirties() {
		JPAQuery query = new JPAQuery(em);
		QContact contact = QContact.contact;
		
		return query.from(contact).where(
					contact.lastname.eq("Dalton").and(
					contact.age.between(30, 40)))
				.list(contact);		
	}

}
