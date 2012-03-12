/**
 * 
 */
package com.zenika.nordnet.repository;

import com.zenika.nordnet.model.Contact;

/**
 * @author acogoluegnes
 *
 */
public interface ContactRepository {

	Contact save(Contact contact);
	
	long count();
	
}
