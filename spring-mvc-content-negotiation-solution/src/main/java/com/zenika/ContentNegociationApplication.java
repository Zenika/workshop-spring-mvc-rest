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
		
		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			converters.add(new CsvHttpMessageConverter());
		}
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new LogHandlerInterceptor());
		}
		
	}
	
}
