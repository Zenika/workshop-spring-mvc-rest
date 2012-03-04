/**
 * 
 */
package com.zenika.nordnet.repository;

import org.springframework.data.repository.Repository;

import com.zenika.nordnet.model.Contact;

/**
 * @author acogoluegnes
 *
 */
public interface AddressRepository extends AddressRepositoryCustom,Repository<Contact,Long> {

}
