/**
 * 
 */
package com.zenika.web;

import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author acogoluegnes
 *
 */
@Controller
public class ContactController {
	
	@Autowired ContactRepository contactRepository;

	@RequestMapping(value="/contacts/{id}",method=RequestMethod.GET)
	@ResponseBody
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
	@ResponseBody 
	public List<Contact> contacts() {
		return contactRepository.findAll();
	}
	
	@RequestMapping(value="/contacts",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Contact contact, HttpServletRequest request, HttpServletResponse response) {
		contact = contactRepository.save(contact);
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
