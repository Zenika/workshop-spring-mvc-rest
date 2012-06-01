/**
 * 
 */
package com.zenika.cnamts.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zenika.cnamts.model.Contact;
import com.zenika.cnamts.repository.ContactRepository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author acogoluegnes
 *
 */
@Controller
@RequestMapping("/contacts")
public class ContactController {
	
	@Autowired ContactRepository contactRepository;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Contact> contact(@PathVariable Long id) {		
		Contact contact = contactRepository.findOne(id);
		ResponseEntity<Contact> response = new ResponseEntity<Contact>(
			contact,
			contact == null ? HttpStatus.NOT_FOUND : HttpStatus.OK
		);
		return response;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	// TODO 07 changer la signature pour retourner une liste de ShortContact
	public List<Contact> contacts() {
		List<Contact> contacts = contactRepository.findAll();
		// TODO 04 initialiser une List<ShortContact>
		
		// TODO 05 itérer sur les contacts
		
		// TODO 06 dans la boucle, pour chaque contact :
		//   - créer un ShortContact
		//   - le mapper avec le Contact en cours (prénom et nom)
		//   - créer un Link pointant vers le détail du Contact (rel=self)
		//   - ajouter le Link au ShortContact
		//   - ajouter le ShortContact à la liste de ShortContact !
		
		// TODO 08 retourner la liste de ShortContact
		return contacts;
	}
	
	@RequestMapping(value="/pages",method=RequestMethod.GET)
	@ResponseBody 
	// TODO 19 changer la signature pour retourner une PageResource<Contact>
	public Page<Contact> contactsPages(@RequestParam int page,@RequestParam int size) {
		// TODO 13 OPTIONNEL pagination navigable
		// les éléments peuvent être récupérés sous forme de pages
		// il faudrait rendre ces pages complétement navigable, c'est-à-dire
		// que chaque page contiendra des liens vers la page précédente, la page suivante,
		// la première page et la dernière page.
		// Il va suffire d'enrober une Page (interface Spring Data) 
		// dans une PageResource (classe custom à compléter)
		Pageable pageable = new PageRequest(
			page,size,new Sort("id")
		);
		Page<Contact> pageResult = contactRepository.findAll(pageable);
		// TODO 20 wrapper la Page dans une PageResource et la retourner
		return pageResult;
	}

	// TODO 03 analyser la classe ShortContact
	// la liste de contacts sera constituée d'instances de cette classe
	// noter que ShortContact est une ressource (hérite de ResourceSupport)
	public static class ShortContact extends ResourceSupport {
	
		private String firstname,lastname;

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
	
	}
}
