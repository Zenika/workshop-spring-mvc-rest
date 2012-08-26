/**
 * 
 */
package com.zenika.web;

import com.zenika.model.Contact;

/**
 * @author acogoluegnes
 *
 */
// TODO 01 compléter le contrôleur
// il doit être appelé pour une requête sur /contact
// le contact doit être disponible dans la vue
// la JSP a utiliser est /WEB-INF/views/contact.jsp
public class ContactController {

	
	public String contact() {
		Contact contact = new Contact(
				1L,"Mickey","Mouse"
		);
	
		return null;
	}
	
	// TODO 06 ne retourner qu'un nom logique pour la vue (pas de chemin, ni d'extension)
}
