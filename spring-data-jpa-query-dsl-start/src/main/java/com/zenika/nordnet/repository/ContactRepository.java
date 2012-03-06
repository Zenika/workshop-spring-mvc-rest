/**
 * 
 */
package com.zenika.nordnet.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;

import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends ContactRepositoryCustom,Repository<Contact,Long>,QueryDslPredicateExecutor<Contact> {

	// TODO 06 rendre l'interface ContactRepository capable d'exécuter des Predicates
	
}
