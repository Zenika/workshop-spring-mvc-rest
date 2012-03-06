/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.zenika.nordnet.model.Contact;

/**
 * @author acogoluegnes
 *
 */
public class ContactRepositoryImpl implements ContactRepositoryCustom {
	
	@PersistenceContext EntityManager em;
	
	@Override
	public List<Contact> findOutlawsInMidThirties() {
		// TODO implémenter cette méthode avec l'API Criteria de JPA 2.0
		// il s'agit de récupérer les membres de la famille Dalton qui ont entre 30 et 40
		
		return null;
	}

}
