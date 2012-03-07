/**
 * 
 */
package com.zenika.nordnet.business;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.zenika.nordnet.model.User;
import com.zenika.nordnet.repository.UserRepository;

/**
 * @author acogoluegnes
 *
 */
public class AuthenticationServiceImplTest {

	private UserRepository repo;
	
	private AuthenticationService service;
	
	private final String login = "user1";
	
	private User user;
	
	@Before public void setUp() {
		// TODO 02 créer un mock pour le UserRepository
		
		// TODO 03 passer le mock au service lors de sa création
		service = new AuthenticationServiceImpl(null);
		user = new User();
		user.setLogin(login);
		user.setPassword("secret");
	}
	
	@Test public void userFoundPasswordOk() {
		// TODO 04 implémenter le test
	}
	
	@Test public void userFoundPasswordNOk() {
		// TODO 05 implémenter le test
	}
	
	@Test public void userNotFound() {
		// TODO 06 implémenter le test
	}
	
	@Test public void moreThanOneUserFound() {
		// TODO 07 implémenter le test
	}
	
}
