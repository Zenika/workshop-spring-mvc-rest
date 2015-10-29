/**
 * 
 */
package com.zenika;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zenika.web.CsvHttpMessageConverter;
import com.zenika.web.LogHandlerInterceptor;

/**
 * @author acogoluegnes
 *
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class ContentNegociationApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ContentNegociationApplication.class, args);
	}

	@Configuration
	public static class ContentNegociationConfiguration extends WebMvcConfigurerAdapter {
		
		
		// TODO 19 positionner le CsvHttpMessageConverter (garder les convertisseurs par défaut)
		
		
		// TODO 04 ajouter LogHandlerInterceptor dans les intercepteurs
		// TODO 05 relancer le test (le serveur affiche aussi le header Accept)
		
		
	}
	
}
