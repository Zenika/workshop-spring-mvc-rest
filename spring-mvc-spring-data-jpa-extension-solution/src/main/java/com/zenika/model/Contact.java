/**
 * 
 */
package com.zenika.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * 
 * @author acogoluegnes
 * 
 */
@Entity
@NamedQuery(
	name="Contact.findBySearchCriteria",
	query="from Contact c where lower(c.firstname) = lower(?1) or lower(c.lastname) = lower(?1)"
)
public class Contact implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	private String firstname, lastname;
	
	private int age;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstname=" + firstname + ", lastname="
				+ lastname + ", age=" + age + "]";
	}
	
}
