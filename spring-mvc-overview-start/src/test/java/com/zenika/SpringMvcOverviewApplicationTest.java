/**
 * 
 */
package com.zenika;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestOperations;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringMvcOverviewApplication.class)
@WebIntegrationTest(randomPort=true)
@Ignore
public class SpringMvcOverviewApplicationTest {

	RestOperations tpl = new TestRestTemplate();
	
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
