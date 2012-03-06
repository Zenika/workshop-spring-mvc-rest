/**
 * 
 */
package com.zenika.nordnet.model;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractAuditable;

/**
 * 
 * @author acogoluegnes
 * 
 */
@Entity
public class Contact extends AbstractAuditable<User, Long> {

	private static final long serialVersionUID = 1L;

	private String firstname, lastname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
