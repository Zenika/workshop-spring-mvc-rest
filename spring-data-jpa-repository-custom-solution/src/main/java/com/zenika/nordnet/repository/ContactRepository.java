/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends ContactRepositoryCustom, Repository<Contact,Long> {

	List<Contact> findByLastname(String lastname);
	
}
