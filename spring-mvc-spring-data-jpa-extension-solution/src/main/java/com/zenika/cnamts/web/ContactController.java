/**
 * 
 */
package com.zenika.cnamts.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zenika.cnamts.model.Contact;
import com.zenika.cnamts.repository.ContactRepository;
import com.zenika.cnamts.web.support.Domain;
import com.zenika.cnamts.web.support.Search;

/**
 * @author acogoluegnes
 *
 */
@Controller
public class ContactController {
	
	@Autowired ContactRepository contactRepository;

	@RequestMapping(value="/contacts/{id}",method=RequestMethod.GET)
	public ResponseEntity<Contact> contact(@Domain Contact contact) {		
		ResponseEntity<Contact> response = new ResponseEntity<Contact>(
			contact,
			contact == null ? HttpStatus.NOT_FOUND : HttpStatus.OK
		);
		return response;
	}
	
	@RequestMapping(value="/contacts",method=RequestMethod.GET)
	@ResponseBody 
	public List<Contact> contacts(Pageable pageable, @Search String search) {
		if(search == null) {
			return contactRepository.findAll(pageable).getContent();
		} else {
			return contactRepository.findBySearchCriteria(search, pageable).getContent();
		}
	}
	
	@RequestMapping(value="/contacts/pages",method=RequestMethod.GET)
	@ResponseBody 
	public Page<Contact> contactsPages(Pageable pageable, @Search String search) {
		if(search == null) {
			return contactRepository.findAll(pageable);
		} else {
			return contactRepository.findBySearchCriteria(search, pageable);
		}
	}
	
	@RequestMapping(value="/contacts",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Contact contact, HttpServletRequest request, HttpServletResponse response) {
		contactRepository.save(contact);
		String location = ServletUriComponentsBuilder.fromRequest(request)
			.pathSegment("{id}")
			.build()
			.expand(contact.getId())
			.encode()
			.toUriString();
		response.setHeader("Location", location);
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
