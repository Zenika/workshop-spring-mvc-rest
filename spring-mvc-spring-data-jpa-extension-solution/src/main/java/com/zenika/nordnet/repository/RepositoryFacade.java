/**
 * 
 */
package com.zenika.nordnet.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.zenika.nordnet.model.DomainObject;

/**
 * @author acogoluegnes
 *
 */
@NoRepositoryBean
public interface RepositoryFacade {

	JpaRepository<DomainObject, Serializable> get(String entityName);
	
	JpaRepository<DomainObject, Serializable> get(Class<?> entityClass);
	
}
