/**
 * 
 */
package com.zenika.nordnet.repository;

import org.springframework.data.repository.Repository;

import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends Repository<Contact,Long> {

	// TODO 02 déclarer une méthode pour retrouver par nom
	
	// TODO 04 déclarer une méthode pour retrouver par prénom et nom
	// on suppose que (prénom,nom) est unique
	
	// TODO 06 déclarer une méthode pour retrouver par ville

	// TODO 08 déclarer une méthode pour retrouver par code postal
	
	// TODO 10 déclarer une méthode pour retrouver par code postal sans ambiguité sur le nom de la propriété

	// TODO 12 déclarer une méthode pour retrouver par code poste (recherche partielle : début, fin, contenant)
	
	// TODO 14 déclarer une méthode pour retrouver par plusieurs codes postaux

	// TODO 16 déclarer une méthode pour retrouver les contacts ayant un certain âge ou plus
	
	// TODO 18 déclarer une méthode pour retrouver les contacts dans une tranche d'âge
	
}
