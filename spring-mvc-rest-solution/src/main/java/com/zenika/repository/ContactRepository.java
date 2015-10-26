/**
 * 
 */
package com.zenika.repository;

import com.zenika.model.Contact;

import java.util.List;

/**
 * @author acogoluegnes
 *
 */
public interface ContactRepository {

    Contact findOne(Long id);

    List<Contact> findAll();

    Contact save(Contact contact);

    void delete(Long id);
}
