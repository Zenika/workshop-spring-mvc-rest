/**
 * 
 */
package com.zenika.nordnet.model;

import org.springframework.data.domain.AuditorAware;

/**
 * @author acogoluegnes
 *
 */
public class SimpleAuditorAware implements AuditorAware<User> {
	
	private User user;

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
	 */
	@Override
	public User getCurrentAuditor() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

}
