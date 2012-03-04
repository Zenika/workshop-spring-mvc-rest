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
// TODO 04 hériter de l'interface custom
public interface ContactRepository extends Repository<Contact,Long> {

	List<Contact> findByLastname(String lastname);
	
}
