/**
 * 
 */
package com.zenika;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.web.client.RestTemplate;

import com.zenika.web.HelloWorldController;

/**
 * @author acogoluegnes
 * 
 */
// TODO 10 commenter @Ignore pour que le test soit lancé
@Ignore
public class JavaConfigTest {

	@Rule
	public TemporaryFolder deployDir = new TemporaryFolder();

	Server server;

	@Before
	public void setUp() throws Exception {
		deployerWebAppDansRepTemporaire();
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	@Test
	public void javaConfig() throws Exception {
		RestTemplate tpl = new RestTemplate();
		String page = tpl.getForObject(
				"http://localhost:8080/hello-world/zen-contact/hello",
				String.class);
		// TODO 11 décommenter (et changer le nom de la méthode si nécessaire)
		// Assert.assertEquals(new HelloWorldController().hello(),page);
	}

	private void deployerWebAppDansRepTemporaire() throws IOException,
			Exception {
		server = new Server();
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		String app = "hello-world";

		// nécessaire pour que le WebApplicationInitializer doit détecté
		File webInfClasses = new File(deployDir.getRoot(), "WEB-INF/classes");
		FileUtils.forceMkdir(webInfClasses);
		FileUtils.copyDirectory(new File("./target/classes"), webInfClasses);

		WebAppContext wac = new WebAppContext();
		List<String> configs = new ArrayList<String>(Arrays.asList(wac
				.getConfigurationClasses()));
		// support pour @HandlesTypes 
		configs.add(AnnotationConfiguration.class.getName());
		wac.setConfigurationClasses(configs.toArray(new String[] {}));
		wac.setContextPath("/" + app);
		wac.setWar(deployDir.getRoot().getAbsolutePath());
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
	}
}
