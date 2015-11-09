/**
 * 
 */
package com.zenika.repository;

import com.zenika.model.Contact;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author acogoluegnes
 *
 */
public interface ContactRepository {

    Contact findOne(Long id);

    List<Contact> findAll();
    
    Page<Contact> findAll(Pageable pageable);

    Contact save(Contact contact);

    void delete(Long id);
}
