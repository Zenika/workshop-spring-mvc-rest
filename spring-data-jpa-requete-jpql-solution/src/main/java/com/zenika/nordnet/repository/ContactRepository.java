/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.zenika.nordnet.model.Address;
import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends Repository<Contact,Long> {

	@Query("from Contact c join fetch c.address where c.lastname = ?1")
	List<Contact> findByLastname(String lastname);
	
	@Query("from Contact c join fetch c.address where c.firstname = :firstname and c.lastname = :lastname")
	Contact findByFirstnameAndLastname(
			@Param("firstname") String firstname, 
			@Param("lastname") String lastname);

	List<Contact> findByAgeGreaterThan(int age);
	
	@Modifying
	@Query("update Contact c set c.address = ?1 where c.lastname = ?2")
	@Transactional
	void setNewAddress(Address address,String lastname);
		
}
