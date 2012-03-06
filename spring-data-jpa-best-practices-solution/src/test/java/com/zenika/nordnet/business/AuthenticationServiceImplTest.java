/**
 * 
 */
package com.zenika.nordnet.business;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.jpa.domain.Specification;

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
		repo = mock(UserRepository.class);
		service = new AuthenticationServiceImpl(repo);
		user = new User();
		user.setLogin(login);
		user.setPassword("secret");
	}
	
	@Test public void userFoundPasswordOk() {
		when(repo.findOne(any(Specification.class)))
			.thenReturn(user);
		Assert.assertEquals(user,service.authenticate(login, "secret"));
	}
	
	@Test public void userFoundPasswordNOk() {
		when(repo.findOne(any(Specification.class)))
			.thenReturn(user);
		Assert.assertNull(service.authenticate(login, "bad"));
	}
	
	@Test public void userNotFound() {
		when(repo.findOne(any(Specification.class)))
			.thenReturn(null);
		Assert.assertNull(service.authenticate(login, "bad"));
	}
	
	@Test public void moreThanOneUserFound() {
		when(repo.findOne(any(Specification.class)))
			.thenThrow(new IncorrectResultSizeDataAccessException(1));
		Assert.assertNull(service.authenticate(login, "secret"));
	}
	
}
