/**
 * 
 */
package com.zenika.nordnet.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.support.RepositoryFactoryInformation;

import com.zenika.nordnet.model.DomainObject;

/**
 * @author acogoluegnes
 *
 */
public class SpringDataRepositoryFacade implements RepositoryFacade, ApplicationContextAware {
	
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
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void setApplicationContext(ApplicationContext context) {
		Map<String,Repository> repos = context.getBeansOfType(Repository.class);
		for (String key : repos.keySet()) {
			RepositoryFactoryInformation info = (RepositoryFactoryInformation) context.getBean("&"+key);

			EntityInformation<DomainObject, Serializable> metadata = info.getEntityInformation();
			Class<JpaRepository<DomainObject, Serializable>> objectType =	info.getRepositoryInterface();
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
