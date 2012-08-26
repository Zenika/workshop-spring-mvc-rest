/**
 * 
 */
package com.zenika.web.support;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zenika.model.DomainObject;
import com.zenika.repository.RepositoryFacade;

/**
 * @author acogoluegnes
 *
 */
public class DomainClassHandlerMethodArgumentResolver implements
		HandlerMethodArgumentResolver {
	
	@Autowired RepositoryFacade repositoryFacade;

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Domain.class);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		List<String> pathSegments = ServletUriComponentsBuilder.fromRequest(webRequest.getNativeRequest(HttpServletRequest.class))
			.build().getPathSegments();
		String id = pathSegments.get(pathSegments.size()-1);
		JpaRepository<DomainObject, Serializable> repo = repositoryFacade.get(parameter.getParameterType());
		if(repo == null) {
			String entityName = pathSegments.get(pathSegments.size()-2);
			repo = repositoryFacade.get(entityName);
		}
		return repo.findOne(Long.valueOf(id));
	}

}
