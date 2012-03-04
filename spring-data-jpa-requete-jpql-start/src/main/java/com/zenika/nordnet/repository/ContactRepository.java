/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.zenika.nordnet.model.Address;
import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends Repository<Contact,Long> {

	// TODO 01 implémenter cette requête en JPQL avec l'annotation appropriée
	// Contraintes :
	//   - l'addresse de chaque contact doit être aussi récupérée
	//   - une seule requête SQL doit être envoyée à la base de données
	List<Contact> findByLastname(String lastname);
	
	// TODO 04 implémenter cette requête en JPQL avec l'annotation appropriée
	// Contraintes :
	//   - l'addresse de chaque contact doit être aussi récupérée
	//   - une seule requête SQL doit être envoyée à la base de données
	//   - binder les paramètres de la requête JQPL avec ceux de
	//     de la méthode Java grâce à une annotation
	Contact findByFirstnameAndLastname(
			@Param("firstname") String firstname, 
			@Param("lastname") String lastname);

	// TODO 06 implémenter cette requête en JPQL en tant que requête nommée, sur la classe d'entité
	// Contraintes :
	//   - l'addresse de chaque contact doit être aussi récupérée
	//   - une seule requête SQL doit être envoyée à la base de données
	List<Contact> findByAgeGreaterThan(int age);
	
	// TODO 08 implémenter cette requête de modification en JPQL
	// il s'agit de positionner l'adresse donnée à tous les contacts ayant le nom donné
	void setNewAddress(Address address,String lastname);
		
}
