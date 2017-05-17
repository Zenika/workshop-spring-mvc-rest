/**
 * 
 */
package com.zenika;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringMvcOverviewApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class SpringMvcOverviewApplicationTest {

	TestRestTemplate tpl = new TestRestTemplate();
	
	@Value("${local.server.port}") int port;
	
	@Test public void helloOk() {
		String message = tpl.getForObject("http://localhost:"+port+"/hello", String.class);
		Assert.assertEquals("Hello World!", message);
	}
	
	@Test public void applicationOk() {
		String message = tpl.getForObject("http://localhost:"+port+"/contact", String.class);
		Assert.assertTrue(message.contains("Mickey"));
		Assert.assertTrue(message.contains("Mouse"));
	}
	
}
