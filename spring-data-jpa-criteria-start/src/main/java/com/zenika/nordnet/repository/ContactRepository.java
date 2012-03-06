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
public interface ContactRepository extends ContactRepositoryCustom,Repository<Contact,Long> {

	// TODO créer une classe ContactSpecs qui contiendra les Specifications
	
	// TODO dans ContactSpecs, définir 1 Specification pour récupérer les Dalton ("outlaws")
	
	// TODO dans ContactSpecs, définir 1 Specification pour récupérer les contacts qui ont entre 30 et 40 ans (inMidThirties)
	
	// TODO rendre l'interface ContactRepository capable d'exécuter des Specifications
	
}