/**
 * 
 */
package com.zenika.nordnet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-jpa-hello-world.xml")
public class SpringDataJpaHelloWorldTest {

	// TODO 04 injecter le repository

	@Test public void springJpa() {
		// TODO 05 créer et insérer un Contact
		// TODO 06 vérifier que le Contact a bien été inséré
	}

}
