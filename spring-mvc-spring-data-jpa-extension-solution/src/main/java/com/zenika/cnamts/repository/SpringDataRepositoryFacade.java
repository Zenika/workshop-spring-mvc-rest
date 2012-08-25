/**
 * 
 */
package com.zenika.cnamts.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.support.RepositoryFactoryInformation;

import com.zenika.cnamts.model.DomainObject;
import com.zenika.cnamts.repository.support.RepositoryMetadataProvider;

/**
 * @author acogoluegnes
 *
 */
public class SpringDataRepositoryFacade implements RepositoryFacade, ApplicationContextAware {
	
	private final RepositoryMetadataProvider repositoryMetadataProvider;
	
	public SpringDataRepositoryFacade(
			RepositoryMetadataProvider repositoryMetadataProvider) {
		super();
		this.repositoryMetadataProvider = repositoryMetadataProvider;
	}

	private final Map<EntityInformation<DomainObject, Serializable>, JpaRepository<DomainObject, Serializable>> repositories =
			new HashMap<EntityInformation<DomainObject, Serializable>, JpaRepository<DomainObject, Serializable>>();
	
	private final Map<String,Class<?>> entityNamesToEntityClasses = new HashMap<String, Class<?>>();

	@Override
	public JpaRepository<DomainObject, Serializable> get(Class<?> entityClass) {
		EntityInformation<?, Serializable> info =
				getRepositoryForDomainType(entityClass);
		JpaRepository<DomainObject, Serializable> repository = repositories.get(info);
		return repository;
	}
	
	@Override
	public JpaRepository<DomainObject, Serializable> get(String entityName) {
		// on suppose que les noms d'entités arrivent au pluriel
		return get(entityNamesToEntityClasses.get(singularize(entityName)));
	}
	
	private String singularize(String entityName) {
		String singular = null;
		if(entityName.endsWith("es")) {
			singular = entityName.substring(0, entityName.lastIndexOf("es"));
		} else if(entityName.endsWith("s")) {
			singular = entityName.substring(0, entityName.lastIndexOf("s"));
		} else {
			singular = entityName;
		}
		return singular;
	}

	private EntityInformation<?, Serializable> getRepositoryForDomainType(
			Class<?> domainType) {

		for (EntityInformation<?, Serializable> information : repositories.keySet()) {
			if (domainType.equals(information.getJavaType())) {
				return information;
			}
		}

		return null;
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		for (RepositoryFactoryInformation info : repositoryMetadataProvider.getRepositoryFactoryInformations()) {

			EntityInformation<DomainObject, Serializable> metadata = info.getEntityInformation();
			Class<JpaRepository<DomainObject, Serializable>> objectType =	(Class<JpaRepository<DomainObject, Serializable>>) info.getRepositoryInformation().getRepositoryInterface();
			JpaRepository<DomainObject, Serializable> repository = BeanFactoryUtils.beanOfType(context, objectType);
			
			Class<?> entityClass = metadata.getJavaType();
			entityNamesToEntityClasses.put(
				entityClass.getSimpleName().toLowerCase(),
				entityClass
			);
			
			this.repositories.put(metadata, repository);	
		}		
	}
	
}
