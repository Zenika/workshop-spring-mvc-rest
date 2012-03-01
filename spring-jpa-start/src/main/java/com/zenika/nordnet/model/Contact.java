/**
 * 
 */
package com.zenika.nordnet.model;


/**
 * 
 * @author acogoluegnes
 *
 */
// TODO 01 ajouter les annotations de mapping à l'entité
public class Contact {

	private Long id;
	
	private String firstname,lastname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
