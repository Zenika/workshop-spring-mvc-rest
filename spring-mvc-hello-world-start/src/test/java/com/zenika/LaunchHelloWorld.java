/**
 * 
 */
package com.zenika;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author acogoluegnes
 *
 */
public class LaunchHelloWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO 05 démarrer le conteneur web en lançant ce programme Java
		
		// TODO 06 une fois lancé, vérifier la page http://localhost:8080/hello-world/zen-contact/hello
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		String app = "hello-world";
		
		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/"+app);
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
        
        System.out.println("**** "+app+" launched");
	}

}
