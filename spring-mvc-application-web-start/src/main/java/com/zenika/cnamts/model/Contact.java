/**
 * 
 */
package com.zenika.cnamts.model;

/**
 * @author acogoluegnes
 *
 */
public class Contact {

	private Long id;
	
	private String firstname,lastname;
	
	public Contact(Long id, String firstname, String lastname) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	
	
}
