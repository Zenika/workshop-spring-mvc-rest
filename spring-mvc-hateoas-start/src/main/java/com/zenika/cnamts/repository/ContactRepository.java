/**
 * 
 */
package com.zenika.cnamts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zenika.cnamts.model.Contact;

/**
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends JpaRepository<Contact, Long>{

}
