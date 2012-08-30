/**
 * 
 */
package com.zenika.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zenika.web.HelloWorldController;

/**
 * @author acogoluegnes
 *
 */
@Configuration
@ComponentScan(basePackageClasses=HelloWorldController.class)
@EnableWebMvc
public class HelloWorldConfiguration extends WebMvcConfigurerAdapter {
	
}
