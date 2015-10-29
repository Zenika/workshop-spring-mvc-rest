/**
 * 
 */
package com.zenika.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;

/**
 * @author acogoluegnes
 *
 */
@RestController
public class ContactController {
	
	@Autowired ContactRepository contactRepository;

	// gestion des erreurs solution 1
	@RequestMapping(value="/contacts/{id}",method=RequestMethod.GET)
	public Contact contact(@PathVariable Long id) {		
		Contact contact = contactRepository.findOne(id);
		if(contact == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return contact;
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() { }
	
	@RequestMapping(value="/contacts",method=RequestMethod.GET)
	public List<Contact> contacts() {
		return contactRepository.findAll();
	}
	
	@RequestMapping(value="/contacts",method=RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Contact contact, UriComponentsBuilder uriComponentsBuilder) {
		contact = contactRepository.save(contact);
		URI location = uriComponentsBuilder
			.pathSegment("contacts","{id}")
			.build()
			.expand(contact.getId())
			.encode()
			.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@RequestMapping(value="/contacts/{id}",method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Contact contact) {
		contactRepository.save(contact);
	}
	
	@RequestMapping(value="/contacts/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		contactRepository.delete(id);
	}
}
