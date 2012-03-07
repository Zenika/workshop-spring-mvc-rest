/**
 * 
 */
package com.zenika.nordnet.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.zenika.nordnet.model.User;

/**
 * @author acogoluegnes
 *
 */
public abstract class UserSpecs {

	public static Specification<User> loginIs(final String login) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("login"), login);
			}
		};
	}
	
	public static Specification<User> isActivated() {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("activated"), true);
			}
		};
	}
	
	
	
}
