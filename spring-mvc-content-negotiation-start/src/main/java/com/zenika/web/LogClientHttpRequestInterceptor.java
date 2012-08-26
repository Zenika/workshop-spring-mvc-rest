/**
 * 
 */
package com.zenika.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author acogoluegnes
 *
 */
public class LogClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	
	private static final Logger LOG = LoggerFactory.getLogger(LogClientHttpRequestInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		ClientHttpResponse response = null;
		try {
			if(request.getHeaders().getAccept() != null) {
				LOG.debug("Asking for: "+request.getHeaders().getAccept());
			}
		} finally {
			response = execution.execute(request, body);
		}
		if(response != null && response.getHeaders().getContentType() != null) {
			LOG.debug("Got: "+response.getHeaders().getContentType());
		}
		return response;
	}
	
}
