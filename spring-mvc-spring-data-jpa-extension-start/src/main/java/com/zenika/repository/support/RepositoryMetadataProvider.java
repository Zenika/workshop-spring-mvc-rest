/**
 * 
 */
package com.zenika.repository.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryInformation;

import com.zenika.model.DomainObject;

/**
 * @author acogoluegnes
 *
 */
public class RepositoryMetadataProvider implements ApplicationContextAware {
	
	private Collection<RepositoryFactoryInformation<DomainObject, Serializable>> infos = 
			new ArrayList<RepositoryFactoryInformation<DomainObject,Serializable>>(); 
	
	public Collection<RepositoryFactoryInformation<DomainObject, Serializable>> getRepositoryFactoryInformations() {
		return Collections.unmodifiableCollection(infos);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		Map<String,Repository> repos = context.getBeansOfType(Repository.class);
		for (String key : repos.keySet()) {
			infos.add((RepositoryFactoryInformation<DomainObject,Serializable>) context.getBean("&"+key));
		}
	}
	
}
