/**
 * 
 */
package com.zenika.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author acogoluegnes
 * 
 */
@XmlRootElement(name="contact")
public class Contact {

	public Contact() {}

	public Contact(Long id, String firstname, String lastname, int age) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}

	public Contact(int age, String firstname, String lastname, Long id) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}

	private Long id;

	private String firstname, lastname;
	
	private int age;

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
