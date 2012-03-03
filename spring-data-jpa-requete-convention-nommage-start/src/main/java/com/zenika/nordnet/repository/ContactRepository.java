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

	// TODO 01 déclarer une méthode pour retrouver par nom
	
	// TODO 03 déclarer une méthode pour retrouver par prénom et nom
	// on suppose que (prénom,nom) est unique
	
	// TODO 05 déclarer une méthode pour retrouver par ville

	// TODO 07 déclarer une méthode pour retrouver par code postal
	
	// TODO 09 déclarer une méthode pour retrouver par code postal sans ambiguité sur le nom de la propriété

	// TODO 11 déclarer une méthode pour retrouver par code poste (recherche partielle : début, fin, contenant)
	
	// TODO 13 déclarer une méthode pour retrouver par plusieurs codes postaux

	// TODO 15 déclarer une méthode pour retrouver les contacts ayant un certain âge ou plus
	
	// TODO 17 déclarer une méthode pour retrouver les contacts dans une tranche d'âge
	
}
