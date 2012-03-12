/**
 * 
 */
package com.zenika.nordnet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zenika.nordnet.model.Contact;
import com.zenika.nordnet.repository.ContactRepository;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-proxies.xml")
public class SpringProxiesTest {

	@Autowired private ContactRepository targetRepo;
	
	@Autowired private PlatformTransactionManager tm;
	
	private ContactRepository proxyRepo;
	
	@Before public void setUp() {
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {			
				TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());
				try {
					Object res = method.invoke(targetRepo, args);
					tm.commit(status);
					return res;
				} catch(Exception e) {
					status.setRollbackOnly();
					tm.rollback(status);
					throw e;
				}				
			}
		};
		proxyRepo = (ContactRepository) Proxy.newProxyInstance(
			getClass().getClassLoader(), 
			new Class<?>[]{ContactRepository.class}, 
			handler
		);
	}
	
	@Test public void springProxies() {
		long initialCount = proxyRepo.count();
		Contact contact = new Contact();
		contact.setFirstname("Mickey");
		contact.setLastname("Mouse");
		proxyRepo.save(contact);
		Assert.assertEquals(
			initialCount+1,
			proxyRepo.count()
		);
		
	}
	
}
