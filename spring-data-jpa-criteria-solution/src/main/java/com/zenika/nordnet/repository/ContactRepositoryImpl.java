/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.zenika.nordnet.model.Contact;

/**
 * @author acogoluegnes
 *
 */
public class ContactRepositoryImpl implements ContactRepositoryCustom {
	
	@PersistenceContext EntityManager em;
	
	@Override
	public List<Contact> findOutlawsInMidThirties() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Contact> query = builder.createQuery(Contact.class);
		Root<Contact> root = query.from(Contact.class);
		
		Predicate outlaws = builder.equal(root.get("lastname"),"Dalton");
		Predicate inMidThirties = builder.between(root.<Integer>get("age"), 30, 40);
		query.where(builder.and(outlaws,inMidThirties));
		return em.createQuery(query.select(root)).getResultList();
	}

}
