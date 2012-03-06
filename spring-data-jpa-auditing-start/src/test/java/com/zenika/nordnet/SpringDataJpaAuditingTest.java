/**
 * 
 */
package com.zenika.nordnet;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zenika.nordnet.model.Contact;
import com.zenika.nordnet.model.SimpleAuditorAware;
import com.zenika.nordnet.model.User;
import com.zenika.nordnet.repository.ContactRepository;
import com.zenika.nordnet.repository.UserRepository;

/**
 * @author acogoluegnes
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-auditing.xml")
public class SpringDataJpaAuditingTest {
	
	@Autowired UserRepository userRepository;

	@Autowired ContactRepository contactRepository;
	
	@Autowired SimpleAuditorAware auditorAware;
	
	@Autowired DataSource ds;
	
	@Before public void setUp() {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.addScript(new ClassPathResource("/data.sql"));
		DatabasePopulatorUtils.execute(pop, ds);
	}

	@Test public void auditing() {
		User creator = new User();
		creator.setLogin("god");
		userRepository.save(creator);
		User modifier = new User();
		modifier.setLogin("man");
		userRepository.save(modifier);
	
		// TODO 04 positionner "creator" comme utilisateur courant
		Contact contact = new Contact();
		contact.setFirstname("Joe");
		contact.setLastname("Dalton");
		// TODO 05 sauvegarder le contact
		
		// TODO 06 récupérer le contact avec son identifiant
		
		// TODO 07 vérifier que createdBy et lastModifiedBy sont positionnés sur "creator"
		// (faire une vérification par identifiant)
		
		
		// TODO 08 positionner "modifier" comme utilisateur courant
		
		// TODO 09 mettre à jour le prénom du contact (appeler un setter puis repo.save())
		
		// TODO 10 récupérer le contact avec son identifiant

		// TODO 11 vérifier que createdBy et lastModifiedBy sont respectivement positionnés sur "creator" et "modifier"
	}
	
		

}
