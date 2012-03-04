/**
 * 
 */
package com.zenika.nordnet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.zenika.nordnet.model.Address;
import com.zenika.nordnet.model.Contact;

/**
 * 
 * @author acogoluegnes
 *
 */
public interface ContactRepository extends JpaRepository<Contact,Long> {

	// TODO implémenter cette requête en JPQL avec l'annotation appropriée
	// Contraintes :
	//   - l'addresse de chaque contact doit être aussi récupérée
	//   - une seule requête SQL doit être envoyée à la base de données
	List<Contact> findByLastname(String lastname);
	
	// TODO implémenter cette requête en JPQL avec l'annotation appropriée
	// Contraintes :
	//   - l'addresse de chaque contact doit être aussi récupérée
	//   - une seule requête SQL doit être envoyée à la base de données
	//   - binder les paramètres de la requête JQPL avec ceux de
	//     de la méthode Java grâce à une annotation
	Contact findByFirstnameAndLastname(
			@Param("firstname") String firstname, 
			@Param("lastname") String lastname);

	// TODO implémenter cette requête en JPQL avec l'annotation appropriée
	// Contraintes :
	//   - l'addresse de chaque contact doit être aussi récupérée
	//   - une seule requête SQL doit être envoyée à la base de données
	List<Contact> findByAgeGreaterThan(int age);
	
	// TODO implémenter cette requête de modification en JPQL
	// il s'agit de positionner l'adresse donnée à tous les contacts ayant le nom donné
	void setNewAddress(Address address,String lastname);
		
}
