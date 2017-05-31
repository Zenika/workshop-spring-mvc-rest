/**
 * 
 */
package com.zenika.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author acogoluegnes
 *
 */
@RestController
public class HelloWorldController {

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
	
}
