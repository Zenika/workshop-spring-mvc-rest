package com.zenika.repository.impl;

import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@Repository
public class InMemoryContactRepository implements ContactRepository {
    
    ConcurrentMap<Long,Contact> rows = new ConcurrentHashMap<>();
    
    AtomicLong counter = new AtomicLong();
    
    public InMemoryContactRepository() {
        rows.put(counter.incrementAndGet(), new Contact(37, "Joe", "Dalton", counter.get()));
        rows.put(counter.incrementAndGet(), new Contact(35, "William", "Dalton", counter.get()));
        rows.put(counter.incrementAndGet(), new Contact(33, "Jack", "Dalton", counter.get()));
        rows.put(counter.incrementAndGet(), new Contact(31, "Averell", "Dalton", counter.get()));

        rows.put(counter.incrementAndGet(), new Contact(78, "Ma", "Dalton", counter.get()));

        rows.put(counter.incrementAndGet(), new Contact(28, "Mickey", "Mouse", counter.get()));
        rows.put(counter.incrementAndGet(), new Contact(25, "Minnie", "Mouse", counter.get()));
        rows.put(counter.incrementAndGet(), new Contact(29, "Donald", "Duck", counter.get()));
        rows.put(counter.incrementAndGet(), new Contact(27, "Daisy", "Duck", counter.get()));

        rows.put(counter.incrementAndGet(), new Contact(8, "Riri", "Duck", counter.get()));
        rows.put(counter.incrementAndGet(), new Contact(8, "Fifi", "Duck", counter.get()));
        rows.put(counter.incrementAndGet(), new Contact(8, "Loulou", "Duck", counter.get()));
    }
    
    @Override
    public Contact findOne(Long id) {
        return rows.get(id);
    }

    @Override
    public Contact save(Contact contact) {
        if(contact.getId() == null) {
            contact.setId(counter.incrementAndGet());
        }
        rows.put(contact.getId(),contact);
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> rowsAsList = new ArrayList<>(rows.values());
        Collections.sort(rowsAsList, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return rowsAsList;
    }

    @Override
    public void delete(Long id) {
        rows.remove(id);
    }
}
