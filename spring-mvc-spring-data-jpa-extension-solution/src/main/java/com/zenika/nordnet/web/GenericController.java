/**
 * 
 */
package com.zenika.nordnet.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zenika.nordnet.model.DomainObject;
import com.zenika.nordnet.repository.RepositoryFacade;
import com.zenika.nordnet.web.support.Domain;

/**
 * @author acogoluegnes
 *
 */
//@Controller
public class GenericController {
	
	@Autowired RepositoryFacade repositoryFacade;

	@RequestMapping(value="/{entityName}/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> contact(@Domain DomainObject domainObject) {		
		ResponseEntity<DomainObject> response = new ResponseEntity<DomainObject>(
			domainObject,
			domainObject == null ? HttpStatus.NOT_FOUND : HttpStatus.OK
		);
		return response;
	}
	
	@RequestMapping(value="/{entityName}",method=RequestMethod.GET)
	@ResponseBody 
	public List<? extends DomainObject> contacts(@PathVariable String entityName) {
		return repositoryFacade.get(entityName).findAll();
	}
	
	@RequestMapping(value="/{entityName}",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody DomainObject domainObject, @PathVariable String entityName,
			HttpServletRequest request, HttpServletResponse response) {
		repositoryFacade.get(entityName).save(domainObject);
		String location = ServletUriComponentsBuilder.fromRequest(request)
			.pathSegment("{id}")
			.build()
			.expand(domainObject.getId())
			.encode()
			.toUriString();
		response.setHeader("Location", location);
	}
	
	@RequestMapping(value="/{entityName}/{id}",method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody DomainObject domainObject,@PathVariable String entityName) {
		repositoryFacade.get(entityName).save(domainObject);
	}
	
	@RequestMapping(value="/{entityName}/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long id, @PathVariable String entityName) {
		repositoryFacade.get(entityName).delete(id);
	}
	
}
