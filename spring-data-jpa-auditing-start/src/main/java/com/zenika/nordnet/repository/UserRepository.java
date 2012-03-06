/**
 * 
 */
package com.zenika.nordnet.repository;

import org.springframework.data.repository.CrudRepository;

import com.zenika.nordnet.model.User;

/**
 * @author acogoluegnes
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
