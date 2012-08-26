/**
 * 
 */
package com.zenika.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zenika.model.Contact;

/**
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	Page<Contact> findBySearchCriteria(String searchCriteria,Pageable pageable);

}
