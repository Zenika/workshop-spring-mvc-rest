/**
 * 
 */
package com.zenika.cnamts.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author acogoluegnes
 *
 */
@Controller
public class HelloWorldController {

	@RequestMapping("/hello")
	@ResponseBody 
	public String hello() {
		return "Hello World!";
	}
	
}
