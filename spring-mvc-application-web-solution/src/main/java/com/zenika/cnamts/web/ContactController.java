/**
 * 
 */
package com.zenika.cnamts.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zenika.cnamts.model.Contact;

/**
 * @author acogoluegnes
 *
 */
@Controller
public class ContactController {

	@RequestMapping("/contact")
	public String contact(Model model) {
		Contact contact = new Contact(
				1L,"Mickey","Mouse"
		);
		model.addAttribute(contact);
		return "contact";
	}
	
}
