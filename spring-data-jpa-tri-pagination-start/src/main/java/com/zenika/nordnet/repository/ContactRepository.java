/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends Repository<Contact,Long> {

	// TODO 01 rendre le tri paramétrable sur cette requête
	List<Contact> findByLastname(String lastname);

	// TODO 04 implémenter la requête en JPQL et rendre le tri paramétrable
	List<Contact> findByCity(String city);
	
	// TODO 06 décommenter la ligne ci-dessous, implémenter la requête en JPQL et rendre la méthode paginable
//	List<Contact> findByCity(String city);
	
	// TODO 08 changer la signature de la méthode (paginable, retourne une page au lien d'une List)
	// TODO 09 implémenter la requête en JPQL (fournir une requête pour le count)
	List<Contact> findPageByCity(String city);
	
}
