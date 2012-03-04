/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends Repository<Contact,Long> {

	List<Contact> findByLastname(String lastname, Sort sort);

	@Query("from Contact c where c.address.city = ?1")
	List<Contact> findByCity(String city,Sort sort);
	
	@Query("from Contact c where c.address.city = ?1")
	List<Contact> findByCity(String city,Pageable pageable);
	
	@Query(
		value="from Contact c where c.address.city = ?1",
		countQuery="select count(*) from Contact c where c.address.city = ?1"
	)
	Page<Contact> findPageByCity(String city,Pageable pageable);
	
}
