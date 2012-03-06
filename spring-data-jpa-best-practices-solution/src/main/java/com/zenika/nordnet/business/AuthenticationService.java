/**
 * 
 */
package com.zenika.nordnet.business;

import com.zenika.nordnet.model.User;

/**
 * @author acogoluegnes
 *
 */
public interface AuthenticationService {

	User authenticate(String login,String password);
	
}
