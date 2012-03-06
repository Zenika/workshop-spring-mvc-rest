/**
 * 
 */
package com.zenika.nordnet.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.zenika.nordnet.model.Contact;

/**
 * @author acogoluegnes
 *
 */
public abstract class ContactSpecs {

	public static Specification<Contact> outlaws() {
		return new Specification<Contact>() {
			@Override
			public Predicate toPredicate(Root<Contact> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("lastname"), "Dalton");
			}
		};
	}
	
	public static Specification<Contact> inMidThirties() {
		return new Specification<Contact>() {
			@Override
			public Predicate toPredicate(Root<Contact> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.between(root.<Integer>get("age"), 30, 40);
			}
		};
	}
	
}
