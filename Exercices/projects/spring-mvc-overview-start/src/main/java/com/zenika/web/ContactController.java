/**
 * 
 */
package com.zenika.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zenika.model.Contact;

/**
 * @author acogoluegnes
 *
 */
// TODO 05 compléter le contrôleur
// il doit être appelé pour une requête sur /contact
// le contact doit être disponible dans la vue
// la vue à utiliser est "contact"
public class ContactController {

	
	public String contact() {
		Contact contact = new Contact(
				1L,"Mickey","Mouse"
		);
		
		return null;
	}
	
	// TODO 06 regarder la vue src/main/resources/templates/contact.html
	// Le moteur de template utilisé est Mustache. 
	// Spring Boot configure automatiquement un ViewResolver qui "entoure"
	// le nom de vue retourné par le contrôleur par "/templates" et ".html"
	// "contact" devient donc "/templates/contact.html"
	
}
