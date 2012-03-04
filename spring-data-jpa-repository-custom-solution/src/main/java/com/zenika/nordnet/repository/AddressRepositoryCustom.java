/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import com.zenika.nordnet.model.Address;

/**
 * @author acogoluegnes
 *
 */
public interface AddressRepositoryCustom {

	List<Address> findByExample(Address address);
	
}
