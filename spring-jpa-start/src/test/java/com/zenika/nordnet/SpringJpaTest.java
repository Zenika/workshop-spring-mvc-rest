/**
 * 
 */
package com.zenika.nordnet;

import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author acogoluegnes
 *
 */
// TODO 04 ajouter les annotations Spring sur le test
public class SpringJpaTest {

	// TODO 05 déclarer et injecter l'EntityManagerFactory 
	@Autowired private EntityManagerFactory emf;
	
	@Test public void springJpa() {
		// TODO 06 créer et insérer un Contact
		// TODO 07 vérifier que le Contact a bien été inséré
		
		// TODO Bonus : écrire un test qui utilise la configuration Java
	}
	
}
