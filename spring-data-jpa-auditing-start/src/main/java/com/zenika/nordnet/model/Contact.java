/**
 * 
 */
package com.zenika.nordnet.model;

import javax.persistence.Entity;

/**
 * 
 * @author acogoluegnes
 * 
 */
// TODO 02 modifier Contact pour le rendre Auditable
@Entity
public class Contact {

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
