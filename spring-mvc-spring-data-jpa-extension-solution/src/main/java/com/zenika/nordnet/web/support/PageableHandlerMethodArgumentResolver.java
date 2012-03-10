/**
 * 
 */
package com.zenika.nordnet.web.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author acogoluegnes
 *
 */
public class PageableHandlerMethodArgumentResolver implements
		HandlerMethodArgumentResolver {
	
	private static final Logger LOG = LoggerFactory.getLogger(PageableHandlerMethodArgumentResolver.class);

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Pageable.class);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		int page = getIntParameter("page", webRequest);
		if(page == -1) {
			return null;
		}
		int size = getIntParameter("page.size",webRequest);
		size = page == -1 ? 10 : size;
		
		String [] sortProperties = webRequest.getParameterValues("page.sort");
		String directionParam = webRequest.getParameter("page.dir");
		Direction direction = Direction.ASC;
		if(directionParam != null) {
			direction = Direction.valueOf(directionParam.toUpperCase());
		}
		Sort sort = new Sort(direction,sortProperties);
		
		
		PageRequest pageable = new PageRequest(page,size,sort);
		LOG.debug("Generated pageable: "+pageable);
		return pageable;
	}
	
	private int getIntParameter(String name, NativeWebRequest request) {
		String parameter = request.getParameter(name);
		if(parameter == null) {
			return -1;
		} else {
			return Integer.valueOf(parameter);
		}
	}

}
