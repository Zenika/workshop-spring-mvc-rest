/**
 * 
 */
package com.zenika.nordnet.repository;

import org.springframework.data.repository.CrudRepository;

import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends CrudRepository<Contact,Long> {

}
