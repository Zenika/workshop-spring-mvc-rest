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
public interface ContactRepository extends Repository<Contact,Long> {

	List<Contact> findByLastname(String lastname);

	List<Contact> findByAddressCity(String city);

	List<Contact> findByAddressZipCode(String zipCode);
	
	List<Contact> findByAddress_ZipCode(String zipCode);

	List<Contact> findByAddressZipCodeLike(String zipCode);

	List<Contact> findByAddressZipCodeIn(String ... zipCodes);

	Contact findByFirstnameAndLastname(String firstname, String lastname);

	List<Contact> findByAgeGreaterThan(int age);

	List<Contact> findByAgeBetween(int low, int high);
	
}
