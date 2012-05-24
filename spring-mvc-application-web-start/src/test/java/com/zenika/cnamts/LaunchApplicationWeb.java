/**
 * 
 */
package com.zenika.cnamts;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * @author acogoluegnes
 *
 */
public class LaunchApplicationWeb {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO 03 démarrer le conteneur web en lançant ce programme Java
		
		// TODO 04 une fois lancé, vérifier la page http://localhost:8080/application-web/zen-contact/contact
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		String app = "application-web";
		
		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/"+app);
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
        
        System.out.println("**** "+app+" launched");
        
        // TODO 05 arrêter le serveur
        
        // TODO 08 redémarrer le serveur et vérifier la page
	}

}
