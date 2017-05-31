/**
 * 
 */
package com.zenika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author acogoluegnes
 *
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class RestApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

}
